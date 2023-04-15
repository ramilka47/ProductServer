package com.flower.server.repository.catalog

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.core.level_checker.check
import com.flower.server.database.dao.GenreDao
import com.flower.server.database.dao.UserDao
import com.flower.server.repository.UseCase
import com.flower.server.repository.getUser
import com.flower.server.web.models.request.catalog.UpdateGenreNameRequest
import com.flower.server.web.models.response.catalog.UpdateGenreNameResponse
import java.rmi.ServerException

class UpdateGenreNameUseCase(private val genreDao: GenreDao,
                             private val userDao: UserDao,
                             private val iLevelCheckCompositor: ILevelCheckCompositor) : UseCase<UpdateGenreNameRequest, UpdateGenreNameResponse> {

    override suspend fun getResponse(request: UpdateGenreNameRequest, token: String?): UpdateGenreNameResponse {
        val user = getUser(userDao, token)

        return iLevelCheckCompositor.check(user.level){
            if (!genreDao.updateGenre(request.id, request.newName)){
                throw ServerException("can't update genre")
            }
            UpdateGenreNameResponse(request.id, request.newName)
        }
    }
}