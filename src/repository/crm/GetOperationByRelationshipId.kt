package com.flower.server.repository.crm

import com.flower.server.database.dao.RelationshipCustomerToOperationDao
import com.flower.server.helper.execeptions.RequestException
import java.rmi.ServerException

class GetOperationByRelationshipId(private val relationshipCustomerToOperationDao : RelationshipCustomerToOperationDao) {

    suspend fun execute(id : Long) =
        relationshipCustomerToOperationDao.getCustomerToOperation(id)?.operationId
            ?: throw RequestException("can't get operation for relate id=$id")
}