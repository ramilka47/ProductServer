package com.flower.server.database.models

import org.jetbrains.exposed.sql.Table

data class Product(
    val id : String,
    val name : String,
    val description : String,
    val photo : String,
    val count : Int,
    val price : Double,
    val code : String)

object Products : Table(){
    val id = long("id").autoIncrement()
    val name = varchar("name", 256)
    val description = varchar("description", 1024)
    val photo = varchar("photo", 512)
    val count = integer("count")
    val price = double("price")
    val code = varchar("code", 128)

    override val primaryKey = PrimaryKey(id)
}