package com.flower.server.database.modules.user

import com.flower.server.database.models.User

interface IDaoUserGetter {
    suspend fun allUsers() : List<User>
    suspend fun getUserById(id : Long) : User?
    suspend fun getUserByLogin(login : String) : User?
    suspend fun getUserByToken(token : String) : User?
    suspend fun usersSize() : Long
}