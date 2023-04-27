package com.flower.server.repository.stockroom

import com.flower.server.database.dao.OperationPositionDao

class GetAllOperationPositionsUseCase(private val operationPositionDao: OperationPositionDao) {

    suspend fun execute() = operationPositionDao.getAllOperationPosition()
}