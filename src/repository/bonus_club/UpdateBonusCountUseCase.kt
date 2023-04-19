package com.flower.server.repository.bonus_club

import com.flower.server.database.dao.BonusCountDao
import com.flower.server.helper.execeptions.RequestException
import java.rmi.ServerException

class UpdateBonusCountUseCase(private val bonusCountDao : BonusCountDao) {

    suspend fun execute(cardId : Long, bonus : Double) =
        bonusCountDao.updateBonusCount(cardId, bonus).apply {
            if (!this) {
                bonusCountDao.getBonusCount(cardId)?.let {
                    throw ServerException("can't update card of count for cardId=$cardId")
                } ?: throw RequestException("bad cardId=$cardId")
            }
        }
}