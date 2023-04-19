package com.flower.server.web.models.response.general

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.IdRequest
import com.flower.server.web.models.response.stockroom.Product

data class GetProductResponse(val product : Product) : IResponse<IdRequest>