package com.flower.server.repository.catalog

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.core.level_checker.check
import com.flower.server.database.dao.TagDao
import com.flower.server.database.dao.UserDao
import com.flower.server.repository.UseCase
import com.flower.server.repository.getUser
import com.flower.server.web.models.request.catalog.UpdateTagNameRequest
import com.flower.server.web.models.response.catalog.UpdateTagNameResponse
import java.rmi.ServerException

class UpdateTagUseCase(private val tagDao : TagDao,
                       private val userDao: UserDao,
                       private val iLevelCheckCompositor: ILevelCheckCompositor
) : UseCase<UpdateTagNameRequest, UpdateTagNameResponse> {

    override suspend fun getResponse(request: UpdateTagNameRequest, token: String?): UpdateTagNameResponse {
        val user = getUser(userDao, token)
        return iLevelCheckCompositor.check(user.level){
            if (!tagDao.updateTag(request.id, request.newName)){
                throw ServerException("can't update tag")
            }
            UpdateTagNameResponse(request.id, request.newName)
        }
    }
}