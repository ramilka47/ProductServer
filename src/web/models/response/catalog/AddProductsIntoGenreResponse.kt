package com.flower.server.web.models.response.catalog

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.catalog.AddProductsIntoGenreRequest

data class AddProductsIntoGenreResponse(val products : List<Product>) : IResponse<AddProductsIntoGenreRequest>
