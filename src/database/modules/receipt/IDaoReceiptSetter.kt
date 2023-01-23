package com.flower.server.database.modules.receipt

import com.flower.server.database.models.Receipt

interface IDaoReceiptSetter {
    suspend fun addReceipt(productId : Long,
                           description : String,
                           date : Long,
                           count : Int,
                           name : String,
                           salePrice : Double) : Receipt?
    suspend fun updateReceipt(id : Long,
                              productId : Long,
                              description : String,
                              date : Long,
                              count : Int,
                              name : String,
                              salePrice : Double) : Boolean
    suspend fun deleteReceipt(id : Long) : Boolean
}