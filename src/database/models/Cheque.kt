package com.flower.server.database.models

import org.jetbrains.exposed.sql.Table

data class Cheque(
    val id : Long,
    val price : Double,
    val name : String,
    val sellings : String,
    val date : Long,
    val canceled : Boolean)

object Cheques : Table(){
    val id = long("id").autoIncrement()
    val price = double("price")
    val name = varchar("name", 256)
    val sellings = text("sellings")
    val date = long("date")
    val canceled = bool("canceled")

    override val primaryKey = PrimaryKey(id)
}