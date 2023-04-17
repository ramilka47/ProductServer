package com.flower.server.repository.crm

import com.flower.server.database.dao.RelationshipCustomerToOperationDao

class GetAllRelationshipForCustomerUseCase(private val relationshipCustomerToOperationDao : RelationshipCustomerToOperationDao) {

    suspend fun execute(customerId : Long) =
        relationshipCustomerToOperationDao.getAllCustomerToOperationByCustomerId(customerId).map { it.operationId }
}