package com.flower.server.database.modules.products

import com.flower.server.database.models.Product
import com.flower.server.models.Sort

interface IDaoProductsGetter {
    suspend fun allProductsByName(name : String) : List<Product>
    suspend fun allProducts() : List<Product>
    suspend fun product(id : Long) : Product?
    suspend fun productByCode(code : String) : Product?
    suspend fun productsByPageSort(sort : Sort, offset : Int) : List<Product>
    suspend fun productByIds(ids : List<Long>) : List<Product>
    suspend fun productsSize() : Long
}