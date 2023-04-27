package com.flower.server.repository.general

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.core.level_checker.check
import com.flower.server.core.price.IPriceComposite
import com.flower.server.database.dao.RelationshipCustomerToOperationDao
import com.flower.server.database.dao.UserDao
import com.flower.server.helper.getCurrentTimeInSec
import com.flower.server.log.Logger
import com.flower.server.repository.UseCase
import com.flower.server.repository.crm.GetCustomerByNameUseCase
import com.flower.server.repository.getUser
import com.flower.server.repository.stockroom.AddOperationWriteOffUseCase
import com.flower.server.repository.stockroom.GetByIdsOperationPositionsUseCase
import com.flower.server.web.models.request.general.WriteOffProductRequest
import com.flower.server.web.models.response.general.WriteOffProductResponse
import com.flower.server.web.models.response.general.toGeneralOperation
import com.flower.server.web.models.response.general.toOperationPosition
import java.rmi.ServerException

//Списание
class WriteOffProductUseCase(
    private val userDao: UserDao,
    private val iLevelCheckCompositor: ILevelCheckCompositor,
    private val addOperationWriteOffUseCase: AddOperationWriteOffUseCase,
    private val getOperationPositionUseCase : GetByIdsOperationPositionsUseCase,
    private val getProductsStockroomUseCase : GetProductsStockroomUseCase,
    private val getCustomerByNameUseCase : GetCustomerByNameUseCase,
    private val relationshipCustomerToOperationDao : RelationshipCustomerToOperationDao,
    private val iPriceComposite: IPriceComposite)
    : UseCase<WriteOffProductRequest, WriteOffProductResponse> {

    override suspend fun getResponse(request: WriteOffProductRequest, token: String?): WriteOffProductResponse {
        val user = getUser(userDao, token)
        return iLevelCheckCompositor.check(user.level) {
            val products = getProductsStockroomUseCase.execute(request.productPays.map { it.productId })
            val operation =
                addOperationWriteOffUseCase
                    .execute(
                        request.productPays.map { it.productId to it.count },
                        request.count,
                        iPriceComposite.execute(products) * request.count,
                        getCurrentTimeInSec()
                    )
            val positionOperations =
                getOperationPositionUseCase
                    .execute(operation.productOperationIds)
                    .map { it.toOperationPosition() }

            try {
                val customer = getCustomerByNameUseCase.execute(user.login)
                relationshipCustomerToOperationDao.addCustomerToOperation(customer.id, operation.id)
            }catch (e : ServerException){
                Logger.logCustomerByNameIsNull(user.login)
            }

            WriteOffProductResponse(
                operation.toGeneralOperation(positionOperations)
            )
        }
    }

}