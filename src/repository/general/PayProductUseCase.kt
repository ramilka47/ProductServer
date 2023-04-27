package com.flower.server.repository.general

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.core.level_checker.check
import com.flower.server.core.price.IPriceComposite
import com.flower.server.database.dao.RelationshipCustomerToOperationDao
import com.flower.server.database.dao.UserDao
import com.flower.server.database.models.storage.StorageOperationEnum
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.repository.UseCase
import com.flower.server.repository.crm.GetCustomerByIdUseCase
import com.flower.server.repository.crm.GetCustomerByNameUseCase
import com.flower.server.repository.crm.GetRelationshipByOperationId
import com.flower.server.repository.getUser
import com.flower.server.repository.stockroom.*
import com.flower.server.web.models.request.general.PayProductRequest
import com.flower.server.web.models.response.general.PayProductResponse

class PayProductUseCase(
    private val userDao: UserDao,
    private val iLevelCheckCompositor: ILevelCheckCompositor,
    private val updateOperationUseCase: UpdateOperationUseCase,
    private val getOperationByIdUseCase: GetOperationByIdUseCase,
    private val getRelationshipByOperationId : GetRelationshipByOperationId,
    private val getCustomerByIdUseCase: GetCustomerByIdUseCase,
    private val getOperationPositionUseCase : GetByIdsOperationPositionsUseCase,
    private val getAllOperationByIdsUseCase : GetAllOperationByIdsUseCase,
    private val getCustomerByNameUseCase : GetCustomerByNameUseCase,
    private val relationshipCustomerToOperationDao : RelationshipCustomerToOperationDao,
    private val iPriceComposite: IPriceComposite
) : UseCase<PayProductRequest, PayProductResponse> {

    override suspend fun getResponse(request: PayProductRequest, token: String?): PayProductResponse{
        val operation = getOperationByIdUseCase.execute(request.reserveId)
        if (operation.operation != StorageOperationEnum.BUY){
            throw RequestException("can't return operation with id=${operation.id} cause status=${operation.operation.name}")
        }
        val relationShip = getRelationshipByOperationId.execute(request.reserveId)
        val customer = getCustomerByIdUseCase.execute(relationShip.customerId)
        val userMain = getUser(userDao, token)

    }
}