package com.flower.server.plugins.interactors

import com.flower.server.helper.daoUserGetter
import com.flower.server.helper.execeptions.ExistException
import com.flower.server.models.request.IdRequest
import com.flower.server.models.result.ResultUser
import com.flower.server.models.result.toResultUser

object GetUserByIdInteractor : Interactor<IdRequest, ResultUser> {

    override suspend fun getResponse(request: IdRequest): ResultUser {
        return daoUserGetter.getUserById(request.id.toLong())?.toResultUser() ?: throw ExistException("user is not exist")
    }

}