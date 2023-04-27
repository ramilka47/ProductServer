package com.flower.server.repository.stockroom

import com.flower.server.database.dao.StorageOperationDao
import com.flower.server.helper.execeptions.RequestException

class GetOperationByIdUseCase(private val storageOperationDao: StorageOperationDao) {

    suspend fun execute(operationId : Long) =
        storageOperationDao
            .getOperation(operationId) ?: throw RequestException("not operation by id=$operationId")
}