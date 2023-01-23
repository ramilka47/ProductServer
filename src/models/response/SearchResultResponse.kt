package com.flower.server.models.response

import com.flower.server.database.models.Product
import com.flower.server.models.IResponse
import com.flower.server.models.request.SearchQueryRequest
import com.flower.server.models.result.ProductResponse

data class SearchResultResponse(val products : List<Product>) : IResponse<SearchQueryRequest>