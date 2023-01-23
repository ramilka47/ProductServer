package com.flower.server.plugins.interactors

import com.flower.server.helper.daoCategorySetter
import com.flower.server.helper.execeptions.ExistException
import com.flower.server.models.request.AddCategoryRequest
import com.flower.server.models.response.AddedCategoryResponse
import com.flower.server.models.response.toAddedCategoryResponse

object PostAddCategoryInteractor : Interactor<AddCategoryRequest, AddedCategoryResponse> {

    override suspend fun getResponse(request: AddCategoryRequest): AddedCategoryResponse {
        return daoCategorySetter.addCategory(request.name)?.toAddedCategoryResponse() ?: throw ExistException("can't add")
    }

}