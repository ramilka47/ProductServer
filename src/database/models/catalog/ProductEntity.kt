package com.flower.server.database.models.product_info

import org.jetbrains.exposed.sql.Table

data class ProductEntity(
    val id : Long,
    val name : String,
    val description : String? = null,
    val photo : String? = null,
    val producer : String? = null)

object ProductsTable : Table(){
    val id = long("id").autoIncrement()
    val name = varchar("name", 256)
    val description = varchar("description", 1024).nullable()
    val photo = varchar("photo", 128).nullable()
    val producer = varchar("producer", 128).nullable()


    override val primaryKey = PrimaryKey(id)
}