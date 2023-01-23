package com.flower.server.database.models

import com.flower.server.database.models.Receipts.autoIncrement
import org.jetbrains.exposed.sql.Table

data class Selling(
    val id : String,
    val product : Product,
    val description : String,
    val date : Long,
    val count : Int,
    val name : String,
    val salePrice : Double)

object Sellings : Table(){
    val id = long("id").autoIncrement()
    val product = varchar("product", 256)
    val description = varchar("description", 1024)
    val date = long("date")
    val count = integer("count")
    val name = varchar("name", 256)
    val salePrice = double("salePrice")

    override val primaryKey = PrimaryKey(id)
}