package com.flower.server.database.models.bonus_club

import org.jetbrains.exposed.sql.Table

data class BonusCardEntity(val id : Long, val name : String, val active : Boolean)

object BonusCardTable : Table(){
    val id = long("id").autoIncrement()
    val name = varchar("name", 256)
    val active = bool("active")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}