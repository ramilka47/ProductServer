package com.flower.server.repository.bonus_club

import com.flower.server.database.dao.BonusCardDao

class ActivateBonusCardResponse(private val bonusCardDao: BonusCardDao) {

    suspend fun execute(cardId : Long, activate : Boolean){
        bonusCardDao.updateBonusCard(id = cardId, active = activate)
    }
}