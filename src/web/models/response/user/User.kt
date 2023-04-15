package com.flower.server.web.models.response.user

import com.flower.server.database.models.admin.UserEntity

class User(val id : Long, val name : String, val login : String, val level : Int)

fun UserEntity.toUser() = User(id, name, login , level)