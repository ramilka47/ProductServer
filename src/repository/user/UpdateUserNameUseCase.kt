package com.flower.server.repository.user

import com.flower.server.database.dao.UserDao
import com.flower.server.helper.NOT_ACCESS
import com.flower.server.helper.execeptions.BadUserTokenException
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.repository.UseCase
import com.flower.server.repository.getUser
import com.flower.server.web.models.request.user.UpdateUserNameRequest
import com.flower.server.web.models.response.user.UpdateUserNameResponse
import java.rmi.ServerException

class UpdateUserNameUseCase(private val userDao: UserDao) : UseCase<UpdateUserNameRequest, UpdateUserNameResponse>{

    override suspend fun getResponse(request: UpdateUserNameRequest, token: String?): UpdateUserNameResponse {
        val user = getUser(userDao, token)
        if (user.id == request.id){
            if (!userDao.updateUser(request.id, name = request.newName)){
                throw ServerException("can't update name")
            }
            return UpdateUserNameResponse(request.id, request.newName)
        } else {
            throw RequestException(NOT_ACCESS)
        }
    }
}