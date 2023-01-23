package com.flower.server.database.modules.user

import com.flower.server.database.models.User

interface IDaoUserSetter {
    suspend fun deleteUser(id : Long) : Boolean
    suspend fun addUser(name : String,
                        login : String,
                        password : String,
                        token : String,
                        date : Long,
                        level : Int) : User?
    suspend fun updateUser(id : Long,
                           name : String,
                           login : String,
                           password : String,
                           token : String,
                           date : Long,
                           level : Int) : Boolean
}