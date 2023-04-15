package com.flower.server.repository.catalog

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.core.level_checker.check
import com.flower.server.database.dao.GenreDao
import com.flower.server.database.dao.UserDao
import com.flower.server.repository.UseCase
import com.flower.server.repository.getUser
import com.flower.server.web.models.request.catalog.CreateGenreRequest
import com.flower.server.web.models.response.catalog.CreateGenreResponse
import java.rmi.ServerException

class CreateGenreUseCase(private val genreDao: GenreDao,
                         private val userDao: UserDao,
                         private val iLevelCheckCompositor: ILevelCheckCompositor) : UseCase<CreateGenreRequest, CreateGenreResponse> {

    override suspend fun getResponse(request: CreateGenreRequest, token: String?): CreateGenreResponse {
        val user = getUser(userDao, token)

        return iLevelCheckCompositor.check(user.level){
            val genre = genreDao.addGenre(request.name) ?: throw ServerException("can't add genre")
            CreateGenreResponse(genre.id, genre.name)
        }
    }
}