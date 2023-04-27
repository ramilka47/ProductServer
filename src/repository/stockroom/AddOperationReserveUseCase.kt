package com.flower.server.repository.stockroom

import com.flower.server.database.dao.CountDao
import com.flower.server.database.dao.OperationPositionDao
import com.flower.server.database.dao.ProductDao
import com.flower.server.database.dao.StorageOperationDao
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.web.models.response.stockroom.ProductOperation
import com.flower.server.web.models.response.stockroom.toProductOperation
import java.rmi.ServerException

class AddOperationReserveUseCase(private val productDao: ProductDao,
                                 private val addOperationPositionUseCase: AddOperationPositionUseCase,
                                 private val storageOperationDao : StorageOperationDao
) {

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
        }
    }
}