package com.flower.server.database.modules.cheque

import com.flower.server.database.models.Cheque

interface IDaoChequeSetter {
    suspend fun addCheque(price : Double,
                          name : String,
                          sellingIds : List<Long>,
                          date : Long) : Cheque?

    suspend fun updateChequeCanceled(id : Long,
                                     canceled: Boolean) : Boolean
}