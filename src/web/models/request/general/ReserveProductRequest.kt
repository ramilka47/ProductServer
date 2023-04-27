package com.flower.server.web.models.request.general

import com.flower.server.web.models.IRequest

data class ReserveProductRequest(val userId : Long? = null, val productPays: List<ProductPays>, val count : Int = 1) : IRequest