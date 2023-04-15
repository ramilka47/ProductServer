package com.flower.server.database.models.crm

import org.jetbrains.exposed.sql.Table

data class FeedbackEntity(val id : Long,
                          val operationId : Long? = null,
                          val productId : Long? = null,
                          val feedback : String,
                          val answer : String? = null,
                          val createDate : Long,
                          val updateDate : Long)

object FeedbackTable : Table(){
    val id = long("id").autoIncrement()
    val operationId = long("operationId").nullable()
    val productId = long("productId").nullable()
    val feedback = varchar("feedback", 512)
    val answer = varchar("answer", 512).nullable()
    val createDate = long("createDate")
    val updateDate = long("updateDate")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}