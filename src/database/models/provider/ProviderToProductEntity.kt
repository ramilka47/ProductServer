package com.flower.server.database.models.provider

import org.jetbrains.exposed.sql.Table

data class ProviderToProductEntity(val id : Long,
                                   val providerId : Long,
                                   val productId : Long)

object ProviderToProductTable : Table(){
    val id = long("id").autoIncrement()
    val providerId = long("providerId")
    val productId = long("productId")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}