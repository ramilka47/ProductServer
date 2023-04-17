package com.flower.server.repository.crm

import com.flower.server.database.dao.RelationshipCustomerToOperationDao

class GetAllRelationshipCustomerToOperationUseCase(private val relationshipCustomerToOperationDao : RelationshipCustomerToOperationDao) {

    suspend fun execute() =
        relationshipCustomerToOperationDao.getAllCustomerToOperation()
}