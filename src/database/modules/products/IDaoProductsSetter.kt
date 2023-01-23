package com.flower.server.database.modules.products

import com.flower.server.database.models.Product

interface IDaoProductsSetter {
    suspend fun addProduct(name : String,
                           description : String,
                           photo : String,
                           count : Int,
                           price : Double,
                           code : String) : Product?
    suspend fun updateProduct(id : Long,
                              name : String,
                              description : String,
                              photo : String,
                              count : Int,
                              price : Double,
                              code : String) : Boolean
    suspend fun updateCountProduct(id : Long, count : Int) : Boolean
    suspend fun deleteProduct(id : Long) : Boolean
}