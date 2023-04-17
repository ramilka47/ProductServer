package com.flower.server.repository.stockroom

import com.flower.server.database.dao.CountDao
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.repository.UseCase
import com.flower.server.web.models.request.IdRequest
import com.flower.server.web.models.response.stockroom.GetProductCountResponse
import com.flower.server.web.models.response.stockroom.toProductCount

class GetProductCountUseCase(private val countDao: CountDao) : UseCase<IdRequest, GetProductCountResponse> {

    override suspend fun getResponse(request: IdRequest, token: String?): GetProductCountResponse =
        GetProductCountResponse(countDao.getCount(request.id)?.toProductCount()
            ?: throw RequestException("count is null for this id"))
}