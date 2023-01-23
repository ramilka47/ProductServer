package com.flower.server.database.modules.categoryOfProducts

import com.flower.server.database.models.CategoryOfProduct

interface IDaoCategoryOfProductsGetter {
    suspend fun allCategoryOfProducts() : List<CategoryOfProduct>
    suspend fun categoryOfProducts(id : Long) : CategoryOfProduct?
    suspend fun allProductOfCategoryId(categoryId : Long) : List<CategoryOfProduct>
    suspend fun allProductOfCategoryProductIds(productIds : List<Long>) : List<CategoryOfProduct>
}