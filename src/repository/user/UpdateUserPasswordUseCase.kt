package com.flower.server.repository.user

import com.flower.server.database.dao.UserDao
import com.flower.server.helper.NOT_ACCESS
import com.flower.server.helper.execeptions.BadUserTokenException
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.repository.UseCase
import com.flower.server.repository.getUser
import com.flower.server.web.models.request.user.UpdatePasswordRequest
import com.flower.server.web.models.response.user.UpdatePasswordResponse
import java.rmi.ServerException

class UpdateUserPasswordUseCase(private val userDao: UserDao) : UseCase<UpdatePasswordRequest, UpdatePasswordResponse> {

    override suspend fun getResponse(request: UpdatePasswordRequest, token: String?): UpdatePasswordResponse {
        val user = getUser(userDao, token)
        if (user.id == request.id){
            if (!userDao.updateUser(request.id, password = request.newPassword)){
                throw ServerException("can't update name")
            }
            return UpdatePasswordResponse(request.id, request.newPassword)
        } else {
            throw RequestException(NOT_ACCESS)
        }
    }
}