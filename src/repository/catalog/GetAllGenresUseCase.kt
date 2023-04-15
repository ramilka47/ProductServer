package com.flower.server.repository.catalog

import com.flower.server.database.dao.GenreDao
import com.flower.server.database.models.product_info.GenreEntity
import com.flower.server.repository.UseCase
import com.flower.server.web.models.request.EmptyRequest
import com.flower.server.web.models.response.catalog.GetAllGenresResponse
import com.flower.server.web.models.response.catalog.toGenre

class GetAllGenresUseCase(private val genreDao: GenreDao) : UseCase<EmptyRequest, GetAllGenresResponse> {

    override suspend fun getResponse(request: EmptyRequest, token: String?): GetAllGenresResponse {
        return GetAllGenresResponse(genreDao.getAllGenres().map(GenreEntity::toGenre))
    }
}