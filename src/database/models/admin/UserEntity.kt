package com.flower.server.database.models.admin

import org.jetbrains.exposed.sql.Table

data class UserEntity(val id : Long,
                      val name : String,
                      val login : String,
                      val password : String,
                      val level : Int,
                      val token : String)

object UserTable : Table(){
    val id = long("id").autoIncrement()
    val name = varchar("name", 128)
    val login = varchar("login", 128)
    val password = varchar("password", 128)
    val level = integer("level")
    val token = varchar("token", 128)

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}