package com.flower.server.database.models.bonus_club

import org.jetbrains.exposed.sql.Table

data class BonusCountEntity(val bonusCardId : Long,
                            val bonus : Double)

object BonusCountTable : Table(){
    val bonusCardId = long("bonusCardId")
    val bonus = double("bonus")

    override val primaryKey: PrimaryKey = PrimaryKey(bonusCardId)
}