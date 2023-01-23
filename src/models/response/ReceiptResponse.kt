package com.flower.server.models.response

import com.flower.server.database.models.Product
import com.flower.server.database.models.Receipt
import com.flower.server.models.IResponse
import com.flower.server.models.request.IdRequest

data class ReceiptResponse(
    val id : String,
    val product : Product,
    val description : String,
    val date : Long,
    val count : Int,
    val name : String,
    val salePrice : Double
) : IResponse<IdRequest>

fun Receipt.toReceiptResponse() = ReceiptResponse(id, product, description, date, count, name, salePrice)