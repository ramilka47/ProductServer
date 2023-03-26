package com.flower.server.database.models.storage

import org.jetbrains.exposed.sql.Table

data class ProductCountEntity(val productId : Long,
                              val count : Int)

object ProductCountTable : Table(){
    val productId = long("productId")
    val count = integer("count")

    override val primaryKey = PrimaryKey(productId)
}