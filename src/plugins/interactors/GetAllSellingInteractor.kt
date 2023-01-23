package com.flower.server.plugins.interactors

import com.flower.server.helper.daoSellingGetter
import com.flower.server.models.request.EmptyRequest
import com.flower.server.models.response.AllSellingResponse
import com.flower.server.models.response.toSellingResponse

object GetAllSellingInteractor : Interactor<EmptyRequest, AllSellingResponse> {

    override suspend fun getResponse(request: EmptyRequest): AllSellingResponse {
        return AllSellingResponse(daoSellingGetter.allSelling().map { it.toSellingResponse() })
    }

}