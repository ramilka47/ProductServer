package com.flower.server.web.models.response.catalog

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.catalog.GetProductByNameRequest

data class GetProductByNameResponse(val products : List<Product>) : IResponse<GetProductByNameRequest>