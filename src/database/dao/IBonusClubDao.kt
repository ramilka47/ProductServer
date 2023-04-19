package com.flower.server.database.dao

import com.flower.server.database.models.bonus_club.BonusCardEntity
import com.flower.server.database.models.bonus_club.BonusCountEntity
import com.flower.server.database.models.bonus_club.BonusCountTable

interface IBonusClubDao : BonusCardDao, BonusCountDao

interface BonusCardDao{

    suspend fun addBonusCard(name : String, active : Boolean) : BonusCardEntity?

    suspend fun updateBonusCard(id : Long, name : String? = null, active : Boolean? = null) : Boolean

    suspend fun deleteBonusCard(id : Long) : Boolean

    suspend fun getBonusCard(name : String) : BonusCardEntity?

    suspend fun getBonusCard(id : Long) : BonusCardEntity?

    suspend fun getAllBonusCards() : List<BonusCardEntity>
}

interface BonusCountDao{

    suspend fun addBonusCount(bonusCardId : Long, bonusCount : Double) : BonusCountEntity?

    suspend fun updateBonusCount(bonusCardId : Long, bonusCount : Double) : Boolean

    suspend fun deleteBonusCount(bonusCardId : Long) : Boolean

    suspend fun getBonusCount(bonusCardId: Long) : BonusCountEntity?
}