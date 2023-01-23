package com.flower.server.database.models

import org.jetbrains.exposed.sql.Table

data class User(
    val id : Long,
    val name : String,
    val login : String,
    val password : String,
    val token : String,
    val date : Long,
    val level : Int)

object Users : Table(){
    val id = long("id").autoIncrement()
    val name = varchar("name", 256)
    val login = varchar("login", 128)
    val password = varchar("password", 256)
    val token = varchar("token", 256)
    val date = long("date")
    val level = integer("level")

    override val primaryKey = PrimaryKey(id)
}