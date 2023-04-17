package com.flower.server.repository.stockroom

import com.flower.server.database.dao.StorageOperationDao
import com.flower.server.web.models.response.stockroom.ProductOperation
import com.flower.server.web.models.response.stockroom.toProductOperation

class GetAllSalesUseCase(private val storageOperationDao: StorageOperationDao) {

    suspend fun execute() : List<ProductOperation> =
        storageOperationDao.getAllSales().map { it.toProductOperation() }

}