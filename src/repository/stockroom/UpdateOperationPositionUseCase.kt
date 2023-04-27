package com.flower.server.repository.stockroom

import com.flower.server.database.dao.OperationPositionDao

class UpdateOperationPositionUseCase(private val operationPositionDao: OperationPositionDao) {

    suspend fun execute(id : Long, productId : Long? = null, count : Int? = null) =
        operationPositionDao.updateOperationPosition(id, productId, count)
}