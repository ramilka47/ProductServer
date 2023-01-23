package com.flower.server.plugins.interactors

import com.flower.server.database.models.Selling
import com.flower.server.helper.Constants
import com.flower.server.helper.daoSellingGetter
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.helper.gson
import com.flower.server.models.request.PageRequest
import com.flower.server.models.response.SellingResponse
import com.flower.server.models.response.toSellingResponse
import com.flower.server.models.result.PageResponse

object PostSellingPageInteractor : Interactor<PageRequest, PageResponse<SellingResponse>> {

    override suspend fun getResponse(request: PageRequest): PageResponse<SellingResponse> {
        if (request.page <= 0) {
            throw RequestException()
        }

        val result : List<Selling> = daoSellingGetter.sellingsByPageSort(sort = request.sort, offset = request.page)
        val size = daoSellingGetter.sellingsSize()

        return PageResponse(request.page, (size / Constants.PAGE_SIZE).toInt().dec(), result.map { it.toSellingResponse() })
    }

}