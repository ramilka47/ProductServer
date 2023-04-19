package com.flower.server.repository.bonus_club

import com.flower.server.database.dao.BonusCountDao

class GetBonusCountUseCase(private val bonusCountDao : BonusCountDao) {

    suspend fun execute(cardId : Long) =
        bonusCountDao.getBonusCount(cardId)
}