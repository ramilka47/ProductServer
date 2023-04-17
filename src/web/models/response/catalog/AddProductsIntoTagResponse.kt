package com.flower.server.web.models.response.catalog

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.catalog.AddProductsIntoTagRequest

data class AddProductsIntoTagResponse(val products : List<Product>) : IResponse<AddProductsIntoTagRequest>