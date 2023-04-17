package com.flower.server.repository.stockroom

import com.flower.server.database.dao.CountDao

internal class UpdateProductCountUseCase(private val countDao: CountDao) {

    suspend fun execute(productId : Long, newCount : Int) : Boolean =
        countDao.updateCount(productId, newCount)

}