package com.flower.server.database.models

import org.jetbrains.exposed.sql.Table

data class CategoryOfProduct(val productId : Long,
                             val categoryId : Long)

object CategoryOfProducts : Table(){
    val productId = long("productId")
    val categoryId = long("categoryId")

    override val primaryKey = PrimaryKey(productId)
}