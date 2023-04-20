package com.flower.server.repository.bonus_club

import com.flower.server.database.dao.BonusCardDao
import com.flower.server.helper.execeptions.RequestException
import java.rmi.ServerException

class ActivateBonusCardUseCase(private val bonusCardDao: BonusCardDao) {

    suspend fun execute(cardId : Long, activate : Boolean) : Boolean =
        bonusCardDao.updateBonusCard(id = cardId, active = activate).apply {
            if (!this){
                bonusCardDao.getBonusCard(cardId)?.let {
                    throw ServerException("can't update bonus card")
                } ?: throw RequestException("bad card id =$cardId")
            }
        }
}