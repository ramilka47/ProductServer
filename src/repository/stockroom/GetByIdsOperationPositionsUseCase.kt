package com.flower.server.repository.stockroom

import com.flower.server.database.dao.OperationPositionDao

class GetByIdsOperationPositionsUseCase(private val operationPositionDao: OperationPositionDao) {

    suspend fun execute(ids : List<Long>) = operationPositionDao.getAllOperationPosition(ids)
}