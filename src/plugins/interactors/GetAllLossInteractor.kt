package com.flower.server.plugins.interactors

import com.flower.server.helper.daoLossGetter
import com.flower.server.models.request.EmptyRequest
import com.flower.server.models.response.AllLossResponse
import com.flower.server.models.response.toLossResponse

object GetAllLossInteractor : Interactor<EmptyRequest, AllLossResponse> {

    override suspend fun getResponse(request: EmptyRequest): AllLossResponse {
        return AllLossResponse(daoLossGetter.allLoss().map { it.toLossResponse() })
    }

}