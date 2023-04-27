package com.flower.server.web.models.request.general

import com.flower.server.web.models.IRequest

data class WriteOffProductRequest(val productPays: List<ProductPays>, val count : Int = 1) : IRequest