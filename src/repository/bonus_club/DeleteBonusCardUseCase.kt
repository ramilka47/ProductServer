package com.flower.server.repository.bonus_club

import com.flower.server.database.dao.BonusCardDao
import com.flower.server.helper.execeptions.RequestException
import java.rmi.ServerException

class DeleteBonusCardUseCase(private val bonusCardDao: BonusCardDao,
                             private val deleteBonusCountUseCase: DeleteBonusCountUseCase) {

    suspend fun execute(cardId : Long) : Boolean =
        bonusCardDao.deleteBonusCard(cardId).apply {
            if (this)
                deleteBonusCountUseCase.execute(cardId)
            else{
                bonusCardDao.getBonusCard(cardId)?.let {
                    throw ServerException("can't update card with id=$cardId")
                } ?: throw RequestException("bad card with id=$cardId")
            }
        }
}