package com.flower.server.database.modules.selling

import com.flower.server.database.models.Selling
import com.flower.server.models.Sort

interface IDaoSellingGetter {
    suspend fun allSellingByIds(ids : List<Long>) : List<Selling>
    suspend fun allSelling() : List<Selling>
    suspend fun selling(id : Long) : Selling?
    suspend fun sellingsByPageSort(sort : Sort, offset : Int) : List<Selling>
    suspend fun sellingsSize() : Long
}