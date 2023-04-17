package com.flower.server.web.models.response.stockroom

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.IdRequest

data class GetProductResponse(val product : Product) : IResponse<IdRequest>