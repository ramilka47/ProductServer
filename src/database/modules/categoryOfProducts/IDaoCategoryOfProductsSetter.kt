package com.flower.server.database.modules.categoryOfProducts

import com.flower.server.database.models.CategoryOfProduct

interface IDaoCategoryOfProductsSetter {
    suspend fun addCategoryOfProducts(productId : Long, categoryId : Long) : CategoryOfProduct?
    suspend fun updateCategoryOfProducts(productId : Long, categoryId : Long) : Boolean
    suspend fun deleteCategoryOfProducts(id : Long) : Boolean
}