package com.flower.server.models.response

import com.flower.server.database.models.Product
import com.flower.server.database.models.Selling
import com.flower.server.models.IResponse
import com.flower.server.models.request.IdRequest

class SellingResponse(
    val id : String,
    val product : Product,
    val description : String,
    val date : Long,
    val count : Int,
    val name : String,
    val salePrice : Double) : IResponse<IdRequest>

fun Selling.toSellingResponse() = SellingResponse(id, product, description, date, count, name, salePrice)