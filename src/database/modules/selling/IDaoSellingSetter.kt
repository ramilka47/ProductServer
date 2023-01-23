package com.flower.server.database.modules.selling

import com.flower.server.database.models.Selling

interface IDaoSellingSetter {
    suspend fun addSelling(productId : Long,
                           description : String,
                           date : Long,
                           count : Int,
                           name : String,
                           salePrice : Double) : Selling?
    suspend fun updateSelling(id : Long,
                              productId : Long,
                              description : String,
                              date : Long,
                              count : Int,
                              name : String,
                              salePrice : Double) : Boolean
    suspend fun deleteSelling(id : Long) : Boolean
}