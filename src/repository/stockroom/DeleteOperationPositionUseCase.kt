package com.flower.server.repository.stockroom

import com.flower.server.database.dao.OperationPositionDao

class DeleteOperationPositionUseCase(private val operationPositionDao: OperationPositionDao) {

    suspend fun execute(id : Long) =
        operationPositionDao.deleteOperationPosition(id)
}