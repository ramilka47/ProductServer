package com.flower.server.models.response

import com.flower.server.database.models.Category
import com.flower.server.models.IResponse
import com.flower.server.models.request.AddCategoryRequest

data class AddedCategoryResponse(val id : Long, val name : String) : IResponse<AddCategoryRequest>

fun Category.toAddedCategoryResponse() = AddedCategoryResponse(id, name)