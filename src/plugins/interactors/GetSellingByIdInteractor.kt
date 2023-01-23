package com.flower.server.plugins.interactors

import com.flower.server.helper.daoSellingGetter
import com.flower.server.helper.execeptions.ExistException
import com.flower.server.models.request.IdRequest
import com.flower.server.models.response.SellingResponse
import com.flower.server.models.response.toSellingResponse

object GetSellingByIdInteractor : Interactor<IdRequest, SellingResponse> {

    override suspend fun getResponse(request: IdRequest): SellingResponse {
        return daoSellingGetter.selling(request.id.toLong())?.toSellingResponse() ?: throw ExistException("selling by id not found")
    }

}