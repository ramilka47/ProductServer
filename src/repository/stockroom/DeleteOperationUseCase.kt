package com.flower.server.repository.stockroom

import com.flower.server.database.dao.StorageOperationDao
import com.flower.server.helper.execeptions.RequestException
import java.rmi.ServerException

class DeleteOperationUseCase(private val storageOperationDao : StorageOperationDao) {

    suspend fun execute(id : Long) : Boolean {
        return storageOperationDao.deleteOperation(id).apply {
            if (!this) {
                if (storageOperationDao.getAllOperationsByIds(listOf(id)).isNotEmpty())
                    throw ServerException("can't delete operation")
                else
                    throw RequestException("operation bad id=$id")
            }
        }
    }
}