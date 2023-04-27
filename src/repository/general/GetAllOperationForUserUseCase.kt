package com.flower.server.repository.general

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.core.level_checker.check
import com.flower.server.database.dao.*
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.repository.UseCase
import com.flower.server.repository.crm.GetAllRelationshipForCustomerUseCase
import com.flower.server.repository.crm.GetCustomerByNameUseCase
import com.flower.server.repository.getUser
import com.flower.server.repository.stockroom.AddOperationPositionUseCase
import com.flower.server.repository.stockroom.GetAllOperationByIdsUseCase
import com.flower.server.repository.stockroom.GetAllOperationsUseCase
import com.flower.server.repository.stockroom.GetByIdsOperationPositionsUseCase
import com.flower.server.web.models.request.EmptyRequest
import com.flower.server.web.models.response.general.GetAllOperationForUserResponse
import com.flower.server.web.models.response.general.toGeneralOperation
import com.flower.server.web.models.response.general.toGeneralProduct
import com.flower.server.web.models.response.general.toOperationPosition

class GetAllOperationForUserUseCase(
    private val getCustomerByNameUseCase : GetCustomerByNameUseCase,
    private val getAllRelationshipForCustomerUseCase: GetAllRelationshipForCustomerUseCase,
    private val getAllOperationByIdsUseCase: GetAllOperationByIdsUseCase,
    private val getAllByIdsOperationPositionsUseCase: GetByIdsOperationPositionsUseCase,
    private val userDao: UserDao
) : UseCase<EmptyRequest, GetAllOperationForUserResponse> {

    override suspend fun getResponse(request: EmptyRequest, token: String?): GetAllOperationForUserResponse {
        val user = getUser(userDao, token)
        val customerId = getCustomerByNameUseCase.execute(user.login).id
        val relationships = getAllRelationshipForCustomerUseCase.execute(customerId)
        val operations = getAllOperationByIdsUseCase.execute(relationships).map {
            it.toGeneralOperation(getAllByIdsOperationPositionsUseCase.execute(it.productOperationIds).map { it.toOperationPosition() })
        }

        return GetAllOperationForUserResponse(operations)
    }
}