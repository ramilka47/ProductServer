package com.flower.server.plugins.interactors

import com.flower.server.helper.Constants
import com.flower.server.helper.daoLossGetter
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.models.request.PageRequest
import com.flower.server.models.response.LossResponse
import com.flower.server.models.response.toLossResponse
import com.flower.server.models.result.PageResponse

object PostLossPageInteractor : Interactor<PageRequest, PageResponse<LossResponse>> {

    override suspend fun getResponse(request: PageRequest): PageResponse<LossResponse> {
        if (request.page <= 0) {
            throw RequestException()
        }

        val result = daoLossGetter.lossesByPageSort(sort = request.sort, offset = request.page)
        val size = daoLossGetter.lossesSize()

        return PageResponse(request.page, (size / Constants.PAGE_SIZE).toInt().dec(), result.map { it.toLossResponse() })
    }

}