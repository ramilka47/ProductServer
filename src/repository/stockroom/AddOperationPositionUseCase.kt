package com.flower.server.repository.stockroom

import com.flower.server.database.dao.OperationPositionDao
import com.flower.server.database.models.storage.OperationPositionEntity
import java.rmi.ServerException

class AddOperationPositionUseCase(private val operationPositionDao: OperationPositionDao) {

    suspend fun execute(positions : List<Pair<Long, Int>>) : List<OperationPositionEntity> {
        val result = mutableListOf<OperationPositionEntity>()
        positions.forEach {
            val productId = it.first
            result.add(operationPositionDao.addOperationPosition(productId, it.second) ?: throw ServerException("can't add operation position for id=$productId"))
        }
        return result
    }
}