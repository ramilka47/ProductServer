package com.flower.server.models.result

import com.flower.server.database.models.User
import com.flower.server.models.IResponse
import com.flower.server.models.request.IdRequest

data class ResultUser(
    val id : Long,
    val name : String,
    val login : String,
    val password : String,
    val token : String,
    val date : Long,
    val level : Int) : IResponse<IdRequest>

fun User.toResultUser() = ResultUser(
    id,
    name,
    login,
    password,
    token,
    date,
    level
)