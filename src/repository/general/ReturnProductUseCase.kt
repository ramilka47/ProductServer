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
import com.flower.server.repository.stockroom.AddOperationReturnUseCase
import com.flower.server.repository.stockroom.GetAllOperationByIdsUseCase
import com.flower.server.repository.stockroom.GetByIdsOperationPositionsUseCase
import com.flower.server.repository.stockroom.GetOperationByIdUseCase
import com.flower.server.web.models.request.general.ReturnProductRequest
import com.flower.server.web.models.response.general.ReturnProductResponse

class ReturnProductUseCase(
    private val userDao: UserDao,
    private val iLevelCheckCompositor: ILevelCheckCompositor,
    private val addOperationReturnUseCase: AddOperationReturnUseCase,
    private val getOperationByIdUseCase: GetOperationByIdUseCase,
    private val getRelationshipByOperationId : GetRelationshipByOperationId,
    private val getOperationPositionUseCase : GetByIdsOperationPositionsUseCase,
    private val getCustomerByIdUseCase: GetCustomerByIdUseCase,
    private val relationshipCustomerToOperationDao : RelationshipCustomerToOperationDao,
    private val getProductsStockroomUseCase : GetProductsStockroomUseCase,
    private val iPriceComposite: IPriceComposite
)
    : UseCase<ReturnProductRequest, ReturnProductResponse> {

    override suspend fun getResponse(request: ReturnProductRequest, token: String?): ReturnProductResponse {
        val operation = getOperationByIdUseCase.execute(request.operationId)
        if (operation.operation != StorageOperationEnum.BUY){
            throw RequestException("can't return operation with id=${operation.id} cause status=${operation.operation.name}")
        }
        val relationShip = getRelationshipByOperationId.execute(request.operationId)
        val customer = getCustomerByIdUseCase.execute(relationShip.customerId)
        val userMain = getUser(userDao, token)


    }

    private suspend fun getReturnProductResponse(request: ReturnProductRequest){

    }
}