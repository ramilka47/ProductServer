package com.flower.server.repository.stockroom

import com.flower.server.database.dao.StorageOperationDao
import com.flower.server.database.models.storage.StorageOperationEnum
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.web.models.response.stockroom.ProductOperation
import com.flower.server.web.models.response.stockroom.toProductOperation
import java.rmi.ServerException

class UpdateOperationUseCase(private val operationDao: StorageOperationDao) {

    suspend fun execute(id : Long, operation : StorageOperationEnum) : ProductOperation =
        if (!operationDao.updateOperation(id, operation))
            throw RequestException("can't update operation with id=$id")
        else
            operationDao.getAllOperationsByIds(listOf(id)).firstOrNull()?.toProductOperation()
                ?: throw ServerException("can't get operation with id=$id")

}