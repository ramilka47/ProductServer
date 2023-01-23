package com.flower.server.models.response

import com.flower.server.database.models.Product
import com.flower.server.models.IResponse
import com.flower.server.models.request.EmptyRequest

data class AllProductResponse(val products : List<Product>) : IResponse<EmptyRequest>