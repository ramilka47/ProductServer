package com.flower.server.web.models.response.catalog

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.IdRequest

data class GetProductResponse(val product: ProductInfo) : IResponse<IdRequest>