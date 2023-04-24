package com.flower.server.repository.stockroom

import com.flower.server.database.dao.CountDao
import com.flower.server.helper.execeptions.RequestException
import java.rmi.ServerException

class UpdateProductCountUseCase(private val countDao: CountDao) {

    suspend fun execute(productId : Long, newCount : Int) : Boolean =
        countDao.updateCount(productId, newCount).apply {
            if (!this){
                if (countDao.getCount(productId) != null){
                    throw ServerException("can't update count for product=$productId")
                } else {
                    throw RequestException("not count for productId=$productId")
                }
            }
        }

}