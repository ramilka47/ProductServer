package com.flower.server.database.models.crm

import org.jetbrains.exposed.sql.Table

data class CustomerEntity(val id : Long,
                          val name : String?,
                          val phone : String?,
                          val address : String?,
                          val scores : Int?)

object CustomerTable : Table(){
    val id = long("id").autoIncrement()
    val name = varchar("name", 128).nullable()
    val phone = varchar("phone", 16).nullable()
    val address = varchar("address", 128).nullable()
    val scores = integer("scores").nullable()

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}