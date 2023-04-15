package com.flower.server.web.models.response.user

import com.flower.server.web.models.IRequest
import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.EmptyRequest
import com.flower.server.web.models.request.IdRequest

sealed class GetUserResponse<T : IRequest>(val id : Long, val name : String, val login : String, val password : String, val level : Int, val token : String) : IResponse<T>{

    class GetUseByTokenResponse(id : Long, name : String, login : String, password : String, level : Int, token : String)
        : GetUserResponse<EmptyRequest>(id, name, login, password, level, token)

    class GetUserByIdResponse(id : Long, name : String, login : String, password : String, level : Int, token : String)
        : GetUserResponse<IdRequest>(id, name, login, password, level, token)
}
