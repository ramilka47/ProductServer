package com.flower.server.database.models

import com.flower.server.database.models.Losses.autoIncrement
import org.jetbrains.exposed.sql.Table

data class Category(val id : Long,
                    val name : String)

object Categories : Table(){
    val id = long("id").autoIncrement()
    val name = varchar("name", 256)

    override val primaryKey = PrimaryKey(id)
}