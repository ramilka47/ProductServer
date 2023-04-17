package com.flower.server.web.models.response.catalog

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.catalog.AddGenresIntoProductRequest

data class AddGenresIntoProductResponse(val product : ProductInfo) : IResponse<AddGenresIntoProductRequest>
