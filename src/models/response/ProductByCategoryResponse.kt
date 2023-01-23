package com.flower.server.models.response

import com.flower.server.models.IResponse
import com.flower.server.models.request.IdRequest
import com.flower.server.models.result.ProductResponse

data class ProductByCategoryResponse(val products : List<ProductResponse>) : IResponse<IdRequest>