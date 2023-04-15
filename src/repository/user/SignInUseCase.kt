package com.flower.server.repository.user

import com.flower.server.core.generate.IGenerator
import com.flower.server.database.dao.UserDao
import com.flower.server.helper.BAD_LOGIN
import com.flower.server.helper.BAD_PASSWORD
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.repository.UseCase
import com.flower.server.web.models.request.user.SignInRequest
import com.flower.server.web.models.response.user.SignInResponse
import java.rmi.ServerException

class SignInUseCase(private val userDao : UserDao, private val generator : IGenerator) : UseCase<SignInRequest, SignInResponse> {

    override suspend fun getResponse(request: SignInRequest, token: String?): SignInResponse {
        val user = userDao.getUserByLogin(request.login) ?: throw RequestException(BAD_LOGIN)
        if (user.password == request.password){
            val newToken = generator.generateToken()
            if (!userDao.updateUser(user.id, token = newToken)){
                throw ServerException("update user token exception")
            }
            return SignInResponse(user.id, user.name, newToken)
        } else {
            throw RequestException(BAD_PASSWORD)
        }
    }

}