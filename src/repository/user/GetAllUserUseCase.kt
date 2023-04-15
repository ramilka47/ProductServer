package com.flower.server.repository.user

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.core.level_checker.check
import com.flower.server.database.dao.UserDao
import com.flower.server.database.models.admin.UserEntity
import com.flower.server.helper.execeptions.BadUserTokenException
import com.flower.server.repository.UseCase
import com.flower.server.repository.getUser
import com.flower.server.web.models.request.EmptyRequest
import com.flower.server.web.models.response.user.GetAllUserResponse
import com.flower.server.web.models.response.user.toUser

class GetAllUserUseCase(private val userDao: UserDao, private val iLevelCheckCompositor: ILevelCheckCompositor) : UseCase<EmptyRequest, GetAllUserResponse> {

    override suspend fun getResponse(request: EmptyRequest, token: String?): GetAllUserResponse {
        val userEntity = getUser(userDao, token)

        return iLevelCheckCompositor.check(userEntity.level){
            GetAllUserResponse(userDao.getAllUsers().map(UserEntity::toUser))
        }
    }
}