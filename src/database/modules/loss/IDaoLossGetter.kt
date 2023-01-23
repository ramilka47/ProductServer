package com.flower.server.database.modules.loss

import com.flower.server.database.models.Loss
import com.flower.server.models.Sort

interface IDaoLossGetter {
    suspend fun allLoss() : List<Loss>
    suspend fun loss(id : Long) : Loss?
    suspend fun lossesByPageSort(sort : Sort, offset : Int) : List<Loss>
    suspend fun lossesSize() : Long
}