package com.flower.server.database.modules.category

import com.flower.server.database.models.Category

interface IDaoCategoryGetter {
    suspend fun allCategory() : List<Category>
    suspend fun category(id : Long) : Category?
    suspend fun allProductOfCategoryIds(categoryIds : List<Long>) : List<Category>
}