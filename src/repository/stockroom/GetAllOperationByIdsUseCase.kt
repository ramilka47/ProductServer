package com.flower.server.repository.stockroom

import com.flower.server.database.dao.StorageOperationDao
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.web.models.response.stockroom.ProductOperation
import com.flower.server.web.models.response.stockroom.toProductOperation

class GetAllOperationByIdsUseCase(private val storageOperationDao: StorageOperationDao) {

    suspend fun execute(ids : List<Long>) : List<ProductOperation> =
        storageOperationDao.getAllOperationsByIds(ids).map { it.toProductOperation() }.apply {
            if (this.size != ids.size)
                throw RequestException("not true ids list")
        }

}