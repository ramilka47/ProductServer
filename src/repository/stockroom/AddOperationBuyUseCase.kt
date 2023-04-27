package com.flower.server.repository.stockroom

import com.flower.server.database.dao.CountDao
import com.flower.server.database.dao.OperationPositionDao
import com.flower.server.database.dao.ProductDao
import com.flower.server.database.dao.StorageOperationDao
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.log.Logger
import com.flower.server.web.models.response.stockroom.ProductOperation
import com.flower.server.web.models.response.stockroom.toProductOperation
import java.rmi.ServerException

class AddOperationBuyUseCase(private val productDao: ProductDao,
                             private val productCountDao: CountDao,
                             private val addOperationPositionUseCase: AddOperationPositionUseCase,
                             private val updateProductCountUseCase: UpdateProductCountUseCase,
                             private val storageOperationDao : StorageOperationDao) {

    suspend fun execute(positions : List<Pair<Long, Int>>, count : Int, price : Double, date : Long) : ProductOperation {
        val products = productDao.getAllProductByIds(positions.map { it.first })
        if (products.size != positions.size)
            throw RequestException("product with this id not found")

        return (storageOperationDao.addBuy(
            operationPositions = addOperationPositionUseCase
                .execute(positions)
                .map { it.id },
            count = count,
            price = price,
            date = date
        )?.toProductOperation()
            ?: throw ServerException("can't add operation")).apply {
            products.forEach {
                val countEntity = productCountDao.getCount(it.id) ?: throw ServerException("count not added")
                updateProductCountUseCase.execute(countEntity.productId, countEntity.count - (count * count))
            }
        }
    }
}