package com.flower.server.web.models.response.catalog

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.IdRequest

data class GetProductsByNotTagResponse(val products : List<Product>) : IResponse<IdRequest>
