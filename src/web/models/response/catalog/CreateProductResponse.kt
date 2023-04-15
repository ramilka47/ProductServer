package com.flower.server.web.models.response.catalog

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.catalog.CreateProductRequest

data class CreateProductResponse(val product : Product) : IResponse<CreateProductRequest>
