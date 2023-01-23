package com.flower.server.plugins.interactors

import com.flower.server.database.models.User
import com.flower.server.helper.daoUserGetter
import com.flower.server.helper.daoUserSetter
import com.flower.server.helper.execeptions.AuthException
import com.flower.server.helper.execeptions.State
import com.flower.server.helper.execeptions.UpdateModelException
import com.flower.server.helper.generator
import com.flower.server.models.request.LoginUser
import com.flower.server.models.response.LoginResponse

object PostLoginUserInteractor : Interactor<LoginUser, LoginResponse> {

    override suspend fun getResponse(request: LoginUser): LoginResponse {
        val user : User = daoUserGetter.getUserByLogin(request.login) ?: throw AuthException(State.Login)

        if (request.password != user.password){
            throw AuthException(State.Password)
        }

        val generateToken = generator.generateToken()

        if (!daoUserSetter.updateUser(user.id, user.name, user.login, user.password, generateToken, user.date, user.level)){
            throw UpdateModelException()
        }

        return LoginResponse(generateToken)
    }

}