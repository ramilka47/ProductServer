package com.flower.server.repository.stockroom

import com.flower.server.database.dao.CountDao
import com.flower.server.database.dao.ProductDao
import com.flower.server.database.dao.StorageOperationDao
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.log.Logger
import com.flower.server.web.models.response.stockroom.ProductOperation
import com.flower.server.web.models.response.stockroom.toProductOperation
import java.rmi.ServerException

class AddOperationWriteOffUseCase(private val productDao: ProductDao,
                                  private val productCountDao: CountDao,
                                  private val updateProductCountUseCase: UpdateProductCountUseCase,
                                  private val storageOperationDao : StorageOperationDao
) {

    suspend fun execute(productId : Long, count : Int, price : Double, date : Long) : ProductOperation {
        if (productDao.getProduct(productId) == null)
            throw RequestException("product with this id not found")

        val countEntity = productCountDao.getCount(productId) ?: throw ServerException("count is not added for product=$productId")
        if (countEntity.count - count < 0){
            throw RequestException("operation write off is bad. count in stockroom is so few. try less of count")
        }

        return (storageOperationDao.addWriteOff(productId, count, price, date)?.toProductOperation()
            ?: throw ServerException("can't add operation")).apply {
            updateProductCountUseCase.execute(productId, countEntity.count - count)
        }
    }
}