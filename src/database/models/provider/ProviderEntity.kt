package com.flower.server.database.models.provider

import org.jetbrains.exposed.sql.Table

data class ProviderEntity(val id : Long,
                          val name : String,
                          val address : String,
                          val description : String,
                          val phone : String)

object ProviderTable : Table(){
    val id = long("id").autoIncrement()
    val name = varchar("name", 256)
    val address = varchar("address", 256)
    val description = varchar("description", 512)
    val phone = varchar("phone", 64)

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}