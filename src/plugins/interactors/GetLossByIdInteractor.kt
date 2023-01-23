package com.flower.server.plugins.interactors

import com.flower.server.helper.daoLossGetter
import com.flower.server.helper.execeptions.ExistException
import com.flower.server.models.request.IdRequest
import com.flower.server.models.response.LossResponse
import com.flower.server.models.response.toLossResponse

object GetLossByIdInteractor : Interactor<IdRequest, LossResponse> {

    override suspend fun getResponse(request: IdRequest): LossResponse {
        return daoLossGetter.loss(request.id.toLong())?.toLossResponse()
            ?: throw ExistException("loss is not exists")
    }

}