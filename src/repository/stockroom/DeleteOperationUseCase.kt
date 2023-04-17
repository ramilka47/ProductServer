package com.flower.server.repository.stockroom

import com.flower.server.database.dao.StorageOperationDao
import java.rmi.ServerException

class DeleteOperationUseCase(private val storageOperationDao : StorageOperationDao) {

    suspend fun execute(id : Long) : Boolean {
        return storageOperationDao.deleteOperation(id).apply {
            if (!this && storageOperationDao.getAllOperationsByIds(listOf(id)).isNotEmpty())
                throw ServerException("can't delete operation")
        }
    }
}