package com.flower.server.database.models.storage

import org.jetbrains.exposed.sql.Table

data class OperationPositionEntity(val id : Long, val productId : Long, val count : Int)

object OperationPositionTable : Table(){
    val id = long("id").autoIncrement()
    val productId = long("productId")
    val count = integer("count")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}