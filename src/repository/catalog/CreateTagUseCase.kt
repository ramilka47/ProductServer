package com.flower.server.repository.catalog

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.core.level_checker.check
import com.flower.server.database.dao.TagDao
import com.flower.server.database.dao.UserDao
import com.flower.server.repository.UseCase
import com.flower.server.repository.getUser
import com.flower.server.web.models.request.catalog.CreateTagRequest
import com.flower.server.web.models.response.catalog.CreateTagResponse
import java.rmi.ServerException

class CreateTagUseCase(private val tagDao : TagDao,
                       private val userDao: UserDao,
                       private val iLevelCheckCompositor: ILevelCheckCompositor) : UseCase<CreateTagRequest, CreateTagResponse> {

    override suspend fun getResponse(request: CreateTagRequest, token: String?): CreateTagResponse {
        val user = getUser(userDao, token)

        return iLevelCheckCompositor.check(user.level){
            val tag = tagDao.addTag(request.name) ?: throw ServerException("can't add tag")
            CreateTagResponse(tag.id, tag.name)
        }
    }
}