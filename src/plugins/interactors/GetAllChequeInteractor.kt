package com.flower.server.plugins.interactors

import com.flower.server.helper.daoChequeGetter
import com.flower.server.models.request.EmptyRequest
import com.flower.server.models.response.AllChequesResponse
import com.flower.server.models.result.toChequeResponse

object GetAllChequeInteractor : Interactor<EmptyRequest, AllChequesResponse> {

    override suspend fun getResponse(request: EmptyRequest): AllChequesResponse {
        return AllChequesResponse(daoChequeGetter.allCheques().map { it.toChequeResponse() })
    }

}