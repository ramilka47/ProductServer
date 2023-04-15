package com.flower.server.repository.user

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.database.dao.UserDao
import com.flower.server.helper.BAD_ID
import com.flower.server.helper.BAD_LEVEL
import com.flower.server.helper.DEVELOPER_LEVEL
import com.flower.server.helper.execeptions.BadUserTokenException
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.repository.UseCase
import com.flower.server.repository.getUser
import com.flower.server.web.models.request.EmptyRequest
import com.flower.server.web.models.request.IdRequest
import com.flower.server.web.models.response.user.GetUserResponse

class GetUserByIdUseCase(private val userDao: UserDao, private val iLevelCheckCompositor : ILevelCheckCompositor) : UseCase<IdRequest, GetUserResponse<IdRequest>>{

    override suspend fun getResponse(request: IdRequest, token: String?): GetUserResponse<IdRequest> {
        val userEntity = getUser(userDao, token)

        if (userEntity.id != request.id || !iLevelCheckCompositor.check(userEntity.level)){
            throw RequestException(BAD_LEVEL)
        }
        val user = userDao.getUser(request.id) ?: throw RequestException(BAD_ID)
        return GetUserResponse.GetUserByIdResponse(user.id, user.name, user.password, user.token, user.level, user.token)
    }
}

class GetUserByTokenUseCase(private val userDao: UserDao) : UseCase<EmptyRequest, GetUserResponse<EmptyRequest>>{

    override suspend fun getResponse(request: EmptyRequest, token: String?): GetUserResponse<EmptyRequest> {
        val user = userDao.getUserByToken(token ?: throw BadUserTokenException()) ?: throw BadUserTokenException()
        return GetUserResponse.GetUseByTokenResponse(user.id, user.name, user.login, user.password, user.level, user.token)
    }
}