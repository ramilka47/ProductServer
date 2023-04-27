package com.flower.server.repository.general

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.core.level_checker.check
import com.flower.server.core.price.IPriceComposite
import com.flower.server.database.dao.RelationshipCustomerToOperationDao
import com.flower.server.database.dao.UserDao
import com.flower.server.database.models.admin.UserEntity
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.helper.getCurrentTimeInSec
import com.flower.server.log.Logger
import com.flower.server.repository.UseCase
import com.flower.server.repository.crm.AddCustomerWithIgnoreUseCase
import com.flower.server.repository.getUser
import com.flower.server.repository.stockroom.AddOperationReserveUseCase
import com.flower.server.repository.stockroom.GetByIdsOperationPositionsUseCase
import com.flower.server.web.models.request.general.ReserveProductRequest
import com.flower.server.web.models.response.general.ReserveProductResponse
import com.flower.server.web.models.response.general.toGeneralOperation
import com.flower.server.web.models.response.general.toOperationPosition
import java.rmi.ServerException

class ReserveProductUseCase(
    private val userDao: UserDao,
    private val iLevelCheckCompositor: ILevelCheckCompositor,
    private val addOperationReserveUseCase: AddOperationReserveUseCase,
    private val addCustomerWithIgnoreUseCase: AddCustomerWithIgnoreUseCase,
    private val getOperationPositionUseCase : GetByIdsOperationPositionsUseCase,
    private val getProductsStockroomUseCase : GetProductsStockroomUseCase,
    private val relationshipCustomerToOperationDao : RelationshipCustomerToOperationDao,
    private val iPriceComposite: IPriceComposite)
    : UseCase<ReserveProductRequest, ReserveProductResponse> {

    override suspend fun getResponse(request: ReserveProductRequest, token: String?): ReserveProductResponse {
        val user = getUser(userDao, token)
        return request.userId?.let { clientId ->
            iLevelCheckCompositor.check(user.level) {
                getReserveProductResponse(
                    request,
                    userDao.getUser(clientId)
                        ?: throw RequestException("user is null with id=${request.userId}")
                )
            }
        } ?: getReserveProductResponse(request, user)
    }

    private suspend fun getReserveProductResponse(request: ReserveProductRequest, client: UserEntity) : ReserveProductResponse{
        val products = getProductsStockroomUseCase.execute(request.productPays.map { it.productId })
        val price = iPriceComposite.execute(products) * request.count
        val operation = addOperationReserveUseCase.execute(
            request.productPays.map { it.productId to it.count },
            request.count,
            price,
            getCurrentTimeInSec()
        )
        val positionOperations =
            getOperationPositionUseCase
                .execute(operation.productOperationIds)
                .map { it.toOperationPosition() }

        try {
            val customer = addCustomerWithIgnoreUseCase.execute(client.login)
            relationshipCustomerToOperationDao.addCustomerToOperation(customer.id, operation.id)
        } catch (e: ServerException) {
            Logger.logCustomerByNameIsNull(client.login)
        }

        return ReserveProductResponse(operation.toGeneralOperation(positionOperations))
    }
}