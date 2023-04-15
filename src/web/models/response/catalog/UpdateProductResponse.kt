package com.flower.server.web.models.response.catalog

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.catalog.UpdateProductRequest

data class UpdateProductResponse(val product : Product) : IResponse<UpdateProductRequest>