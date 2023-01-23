package com.flower.server.plugins.interactors

import com.flower.server.helper.daoCategoryGetter
import com.flower.server.models.request.EmptyRequest
import com.flower.server.models.response.AllCategoryResponse
import com.flower.server.models.result.CategoryResponse

object GetAllCategoryInteractor : Interactor<EmptyRequest, AllCategoryResponse> {

    override suspend fun getResponse(request: EmptyRequest): AllCategoryResponse {
        return AllCategoryResponse(daoCategoryGetter.allCategory().map { CategoryResponse(it.id, it.name) })
    }

}