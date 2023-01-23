package com.flower.server.models.result

import com.flower.server.database.models.Category
import com.flower.server.models.IResponse
import com.flower.server.models.request.IdRequest

data class CategoryResponse(val id : Long, val name : String) : IResponse<IdRequest>

fun Category.toCategoryResponse() = CategoryResponse(id, name)