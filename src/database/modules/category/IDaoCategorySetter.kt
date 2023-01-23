package com.flower.server.database.modules.category

import com.flower.server.database.models.Category


interface IDaoCategorySetter {
    suspend fun addCategory(name : String) : Category?
    suspend fun updateCategory(id : Long, name : String) : Boolean
    suspend fun deleteCategory(id : Long) : Boolean
}