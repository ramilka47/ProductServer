package com.flower.server.repository.catalog

import com.flower.server.database.dao.TagDao
import com.flower.server.database.models.product_info.TagEntity
import com.flower.server.repository.UseCase
import com.flower.server.web.models.request.EmptyRequest
import com.flower.server.web.models.response.catalog.GetAllTagResponse
import com.flower.server.web.models.response.catalog.toTag

class GetAllTagUseCase(private val tagDao: TagDao) : UseCase<EmptyRequest, GetAllTagResponse> {

    override suspend fun getResponse(request: EmptyRequest, token: String?): GetAllTagResponse {
        return GetAllTagResponse(tagDao.getAllTags().map(TagEntity::toTag))
    }
}