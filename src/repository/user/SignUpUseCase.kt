package com.flower.server.repository.user

import com.flower.server.core.LoginPasswordCheckChars
import com.flower.server.core.generate.IGenerator
import com.flower.server.database.dao.UserDao
import com.flower.server.helper.BAD_LOGIN
import com.flower.server.helper.BAD_PASSWORD
import com.flower.server.helper.CAN_T_ADD_USER
import com.flower.server.helper.LOGIN_IS_EXISTS
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.repository.UseCase
import com.flower.server.web.models.request.user.SignUpRequest
import com.flower.server.web.models.response.user.SignUpResponse
import java.rmi.ServerException

class SignUpUseCase(private val userDao : UserDao,
                    private val iGenerator: IGenerator,
                    private val loginPasswordCheckChars: LoginPasswordCheckChars) : UseCase<SignUpRequest, SignUpResponse> {

    override suspend fun getResponse(request: SignUpRequest, token: String?): SignUpResponse {
        if (!loginPasswordCheckChars.loginCheck(request.login)){
            throw RequestException(BAD_LOGIN)
        }
        if (!loginPasswordCheckChars.loginCheck(request.password)){
            throw RequestException(BAD_PASSWORD)
        }
        if (token == null){
            //todo
        } else {
            //todo
        }

        val userByLogin = userDao.getUserByLogin(request.login)
        if (userByLogin != null){
            throw RequestException(LOGIN_IS_EXISTS)
        }

        val user = userDao.addUser(request.name, request.login, request.password, 1, iGenerator.generateToken())

        return SignUpResponse(user?.token ?: throw ServerException(CAN_T_ADD_USER))
    }

}