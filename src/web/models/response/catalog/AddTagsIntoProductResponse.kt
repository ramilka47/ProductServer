package com.flower.server.web.models.response.catalog

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.catalog.AddTagsIntoProductRequest

data class AddTagsIntoProductResponse(val product : ProductInfo) : IResponse<AddTagsIntoProductRequest>