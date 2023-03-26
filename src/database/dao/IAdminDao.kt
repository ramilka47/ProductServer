package com.flower.server.database.dao

import com.flower.server.database.models.admin.UserEntity

interface IAdminDao : UserDao

interface UserDao{

    suspend fun addUser(name : String,
                        login : String,
                        password : String,
                        level : Int,
                        token : String) : UserEntity?

    suspend fun updateUser(id : Long,
                           name : String? = null,
                           login : String? = null,
                           password : String? = null,
                           level : Int? = null,
                           token : String? = null) : Boolean

    suspend fun deleteUser(id : Long) : Boolean

    suspend fun getUser(id : Long) : UserEntity?

    suspend fun getUserByName(name : String) : UserEntity?

    suspend fun getUserByLogin(login : String) : UserEntity?

    suspend fun getUserByToken(token : String) : UserEntity?

    suspend fun getAllUsers() : List<UserEntity>

}