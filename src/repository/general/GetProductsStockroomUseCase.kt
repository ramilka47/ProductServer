package com.flower.server.repository.general

import com.flower.server.database.dao.CountDao
import com.flower.server.database.dao.ProductDao
import com.flower.server.database.dao.StorageProductDataDao
import com.flower.server.web.models.response.general.ProductDataGeneral

class GetProductsStockroomUseCase(
    private val productDao : ProductDao,
    private val countDao: CountDao,
    private val storageProductDataDao: StorageProductDataDao) {

    suspend fun execute(productIds : List<Long>) : List<ProductDataGeneral> {
        val products = productDao.getAllProductByIds(productIds)
        val productsData = storageProductDataDao.getStorageProductData(productIds)
        val productCount = countDao.getCount(productIds)
        return productIds.map { id->
            val product = products.first { it.id == id }
            val data = productsData.first { it.productId == id }
            val count = productCount.first { it.productId == id }

            ProductDataGeneral(product.id, data.price, data.salePrice, data.discount, count.count)
        }
    }
}