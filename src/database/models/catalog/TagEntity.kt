package com.flower.server.database.models.product_info

import org.jetbrains.exposed.sql.Table

data class TagEntity(val id : Long, val name : String)

object TagTable : Table(){
    val id = long("id").autoIncrement()
    val name = varchar("name", 100)

    override val primaryKey = PrimaryKey(id)
}
