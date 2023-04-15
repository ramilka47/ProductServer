package com.flower.server.database.models.storage

import org.jetbrains.exposed.sql.Table

data class StorageOperationEntity(val id : Long,
                                  val productId : Long,
                                  val operation : StorageOperationEnum,
                                  val count : Int,
                                  val price : Double,
                                  val date : Long)

object StorageOperationTable : Table(){
    val id = long("id").autoIncrement()
    val productId = long("productId")
    val operation = varchar("operation", 20)
    val count = integer("count")
    val price = double("price")
    val date = long("date")

    override val primaryKey = PrimaryKey(id)
}

enum class StorageOperationEnum{
    BUY, SALE, WRITE_OFF, RETURN, RESERVE;
}
