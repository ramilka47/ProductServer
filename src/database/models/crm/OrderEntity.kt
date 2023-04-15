package com.flower.server.database.models.crm

import org.jetbrains.exposed.sql.Table

data class OrderEntity(val id : Long,
                       val customerId : Long,
                       val operationId : Long,
                       val description : String,
                       val address : String,
                       val supplyStatus : SupplyStatus,
                       val createStatus : CreateStatus,
                       val deliveryStatus : DeliveryStatus,
                       val createDate : Long,
                       val updateDate : Long)

object OrderTable : Table(){
    val id = long("id").autoIncrement()
    val customerId = long("customerId")
    val operationId = long("operationId")
    val description = varchar("description", 512)
    val address = varchar("address", 512)
    val supplyStatus = varchar("supplyStatus", 16)
    val createStatus = varchar("createStatus", 16)
    val deliveryStatus = varchar("deliveryStatus", 16)
    val createDate = long("createDate")
    val updateDate = long("updateDate")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}

enum class CreateStatus{
    TODO, IN_PROGRESS, DONE, CANCELED
}

enum class DeliveryStatus{
    TODO, IN_PROGRESS, DONE
}

enum class SupplyStatus{
    TODO, IN_PROGRESS, DONE
}