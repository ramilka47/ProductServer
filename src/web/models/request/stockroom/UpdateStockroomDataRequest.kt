package com.flower.server.web.models.request.stockroom

import com.flower.server.web.models.IRequest

data class UpdateStockroomDataRequest(
    val productId : Long,
    val price : Double? = null,
    val discount : Float? = null,
    val unicode : String? = null) : IRequest