package com.flower.server.database.models.product_info

import org.jetbrains.exposed.sql.Table

data class RelationshipTagProductEntity(val id : Long,
                                        val productId : Long,
                                        val tagId : Long)

object RelationshipTagProductTable : Table(){
    val id = long("id").autoIncrement()
    val productId = long("productId")
    val tagId = long("tagId")

    override val primaryKey = PrimaryKey(id)
}