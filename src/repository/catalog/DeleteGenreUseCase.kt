package com.flower.server.repository.catalog

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.core.level_checker.check
import com.flower.server.database.dao.GenreDao
import com.flower.server.database.dao.UserDao
import com.flower.server.repository.UseCase
import com.flower.server.repository.getUser
import com.flower.server.web.models.request.catalog.DeleteGenreRequest
import com.flower.server.web.models.response.catalog.DeleteGenreResponse
import java.rmi.ServerException

class DeleteGenreUseCase(private val genreDao: GenreDao,
                         private val userDao: UserDao,
                         private val iLevelCheckCompositor: ILevelCheckCompositor
) : UseCase<DeleteGenreRequest, DeleteGenreResponse> {

    override suspend fun getResponse(request: DeleteGenreRequest, token: String?): DeleteGenreResponse {
        val user = getUser(userDao, token)
        return iLevelCheckCompositor.check(user.level){
            if (!genreDao.deleteGenre(request.id)){
                throw ServerException("can't delete genre")
            }
            DeleteGenreResponse(true)
        }
    }
}