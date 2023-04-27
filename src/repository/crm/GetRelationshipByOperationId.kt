package com.flower.server.repository.crm

import com.flower.server.database.dao.RelationshipCustomerToOperationDao
import com.flower.server.helper.execeptions.RequestException

class GetRelationshipByOperationId(private val relationshipCustomerToOperationDao : RelationshipCustomerToOperationDao) {

    suspend fun execute(operationId : Long) =
        relationshipCustomerToOperationDao
            .getByOperationId(operationId) ?: throw RequestException("not relationship by operationId=$operationId")
}