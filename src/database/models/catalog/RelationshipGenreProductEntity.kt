package com.flower.server.database.models.product_info

import org.jetbrains.exposed.sql.Table

data class RelationshipGenreProductEntity(val id : Long,
                                          val productId : Long,
                                          val genreId : Long)

object RelationshipGenreProductTable : Table(){
    val id = long("id").autoIncrement()
    val productId = long("productId")
    val genreId = long("genreId")

    override val primaryKey = PrimaryKey(id)
}