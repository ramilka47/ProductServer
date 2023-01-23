package com.flower.server.plugins.interactors

import com.flower.server.database.models.Receipt
import com.flower.server.helper.Constants
import com.flower.server.helper.daoReceiptGetter
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.models.request.PageRequest
import com.flower.server.models.response.ReceiptResponse
import com.flower.server.models.response.toReceiptResponse
import com.flower.server.models.result.PageResponse

object PostReceiptPageInteractor : Interactor<PageRequest, PageResponse<ReceiptResponse>> {

    override suspend fun getResponse(request: PageRequest): PageResponse<ReceiptResponse> {
        if (request.page <= 0) {
            throw RequestException()
        }

        val result : List<Receipt> = daoReceiptGetter.receiptsByPageSort(sort = request.sort, offset = request.page)
        val size = daoReceiptGetter.receiptsSize()

        return PageResponse(request.page, (size / Constants.PAGE_SIZE).toInt().dec(), result.map { it.toReceiptResponse() })
    }

}