package com.flower.server.repository.bonus_club

import com.flower.server.database.dao.BonusCountDao
import java.rmi.ServerException

class DeleteBonusCountUseCase(private val bonusCountDao : BonusCountDao) {

    suspend fun execute(cardId : Long) =
        bonusCountDao.deleteBonusCount(cardId).apply {
            if (!this){
                bonusCountDao.getBonusCount(cardId)?.let {
                    throw ServerException("can't delete count of bonus card cardId=${cardId}")
                }
                    ?: throw ServerException("bad cardId=$cardId")
            }
        }
}