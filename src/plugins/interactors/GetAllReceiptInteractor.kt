package com.flower.server.plugins.interactors

import com.flower.server.helper.daoReceiptGetter
import com.flower.server.models.request.EmptyRequest
import com.flower.server.models.response.AllReceiptResponse
import com.flower.server.models.response.toReceiptResponse

object GetAllReceiptInteractor : Interactor<EmptyRequest, AllReceiptResponse> {

    override suspend fun getResponse(request: EmptyRequest): AllReceiptResponse {
        return AllReceiptResponse(daoReceiptGetter.allReceipt().map { it.toReceiptResponse() })
    }

}