package com.flower.server.database.modules.receipt

import com.flower.server.database.models.Receipt
import com.flower.server.models.Sort

interface IDaoReceiptGetter {
    suspend fun allReceipt() : List<Receipt>
    suspend fun receipt(id : Long) : Receipt?
    suspend fun receiptsByPageSort(sort : Sort, offset : Int) : List<Receipt>
    suspend fun receiptsSize() : Long
}