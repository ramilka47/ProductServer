package com.flower.server.plugins.interactors

import com.flower.server.helper.daoUserGetter
import com.flower.server.models.request.EmptyRequest
import com.flower.server.models.response.AllUsersResponse
import com.flower.server.models.result.toResultUser

object GetAllUserInteractor : Interactor<EmptyRequest, AllUsersResponse> {

    override suspend fun getResponse(request: EmptyRequest): AllUsersResponse {
        return AllUsersResponse(daoUserGetter.allUsers().map { it.toResultUser() })
    }

}