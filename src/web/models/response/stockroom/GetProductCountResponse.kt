package com.flower.server.web.models.response.stockroom

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.IdRequest

data class GetProductCountResponse(val productCount: ProductCount) : IResponse<IdRequest>