package com.flower.server.models.result

import com.flower.server.database.models.Cheque
import com.flower.server.helper.gson
import com.flower.server.models.IResponse
import com.flower.server.models.request.IdRequest
import com.google.gson.JsonArray

data class ChequeResponse(
    val id : Long,
    val price : Double,
    val name : String,
    val productNames : List<String>,
    val date : Long,
    val canceled : Boolean) : IResponse<IdRequest>

fun Cheque.toChequeResponse() = ChequeResponse(
    id, price, name, gson.fromJson(sellings, JsonArray::class.java).map { it.toString() }, date, canceled
)