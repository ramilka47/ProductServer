package com.flower.server.database.models.storage

import org.jetbrains.exposed.sql.Table

data class StorageProductDataEntity(val productId : Long,
                                    val price : Double,
                                    val salePrice : Double,
                                    val discount : Float,
                                    val uniCode : String)

object StorageProductDataTable : Table(){
    val productId = long("productId")
    val price = double("price")
    val salePrice = double("salePrice")
    val discount = float("discount")
    val uniCode = varchar("uniCode", 128)

    override val primaryKey = PrimaryKey(productId)
}