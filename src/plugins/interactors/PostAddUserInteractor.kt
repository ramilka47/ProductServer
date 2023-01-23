package com.flower.server.plugins.interactors

import com.flower.server.helper.daoUserGetter
import com.flower.server.helper.daoUserSetter
import com.flower.server.helper.execeptions.ExistException
import com.flower.server.helper.generator
import com.flower.server.helper.getCurrentTimeInSec
import com.flower.server.models.request.AddUserRequest
import com.flower.server.models.response.AddedUserResponse

object PostAddUserInteractor : Interactor<AddUserRequest, AddedUserResponse> {

    override suspend fun getResponse(request: AddUserRequest): AddedUserResponse {
        val currentTime = getCurrentTimeInSec()

        if (daoUserGetter.getUserByLogin(request.login) != null){
            throw ExistException("this login is exists")
        }

        val token = generator.generateToken()

        val user = daoUserSetter.addUser(
            request.name,
            request.login,
            request.password,
            token,
            currentTime,
            request.level
        ) ?: throw ExistException("can't add User")

        return AddedUserResponse(user.token)
    }

}