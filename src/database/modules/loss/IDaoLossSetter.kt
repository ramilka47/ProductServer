package com.flower.server.database.modules.loss

import com.flower.server.database.models.Loss

interface IDaoLossSetter {

    suspend fun addLoss(productId : Long,
                        description : String,
                        date : Long,
                        count : Int,
                        name : String,
                        salePrice : Double) : Loss?

    suspend fun updateLoss(id : Long,
                           productId : Long,
                           description : String,
                           date : Long,
                           count : Int,
                           name : String,
                           salePrice : Double) : Boolean

    suspend fun deleteLoss(id : Long) : Boolean
}