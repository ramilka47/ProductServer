package com.flower.server.repository.bonus_club

import com.flower.server.database.dao.BonusCountDao
import com.flower.server.helper.execeptions.RequestException

class AddBonusCountUseCase(private val bonusCountDao : BonusCountDao) {

    suspend fun execute(cardId : Long, bonus : Double) =
        bonusCountDao.addBonusCount(cardId, bonus) ?: throw RequestException("can't add count of card")
}