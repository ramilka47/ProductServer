package com.flower.server.repository.stockroom

import com.flower.server.database.dao.ProductDao
import com.flower.server.database.dao.StorageOperationDao
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.web.models.response.stockroom.ProductOperation
import com.flower.server.web.models.response.stockroom.toProductOperation
import java.rmi.ServerException

class AddOperationReturnUseCase(private val productDao: ProductDao,
                                private val storageOperationDao : StorageOperationDao
) {

    suspend fun execute(productId : Long, count : Int, price : Double, date : Long) : ProductOperation {
        if (productDao.getProduct(productId) == null)
            throw RequestException("product with this id not found")
        return storageOperationDao.addReturn(productId, count, price, date)?.toProductOperation()
            ?: throw ServerException("can't add operation")
    }
}