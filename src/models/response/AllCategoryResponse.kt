package com.flower.server.models.response

import com.flower.server.models.IResponse
import com.flower.server.models.request.EmptyRequest
import com.flower.server.models.result.CategoryResponse

data class AllCategoryResponse(val categories : List<CategoryResponse>) : IResponse<EmptyRequest>