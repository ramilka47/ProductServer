package com.flower.server.repository.bonus_club

import com.flower.server.database.dao.BonusCardDao

class DeleteBonusCardUseCase(private val bonusCardDao: BonusCardDao,
                             private val deleteBonusCountUseCase: DeleteBonusCountUseCase) {

    suspend fun execute(cardId : Long) : Boolean =
        bonusCardDao.deleteBonusCard(cardId).apply {
            if (this)
                deleteBonusCountUseCase.execute(cardId)
        }
}