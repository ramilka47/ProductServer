package com.flower.server.plugins.interactors

import com.flower.server.helper.daoReceiptGetter
import com.flower.server.helper.execeptions.ExistException
import com.flower.server.models.request.IdRequest
import com.flower.server.models.response.ReceiptResponse
import com.flower.server.models.response.toReceiptResponse

object GetReceiptByIdInteractor : Interactor<IdRequest, ReceiptResponse> {

    override suspend fun getResponse(request: IdRequest): ReceiptResponse {
        return daoReceiptGetter.receipt(request.id.toLong())?.toReceiptResponse()
            ?: throw ExistException("receipt by id not found")

    }

}