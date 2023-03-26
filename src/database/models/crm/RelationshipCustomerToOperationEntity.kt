package com.flower.server.database.models.crm

import org.jetbrains.exposed.sql.Table

data class RelationshipCustomerToOperationEntity(val id : Long, val customerId : Long, val operationId : Long)

object RelationshipCustomerToOperationTable : Table(){
    val id = long("id").autoIncrement()
    val customerId = long("customerId")
    val operationId = long("operationId")

    override val primaryKey: PrimaryKey? = PrimaryKey(id)
}
