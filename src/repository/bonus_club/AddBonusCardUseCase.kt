package com.flower.server.repository.bonus_club

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.core.level_checker.check
import com.flower.server.database.dao.BonusCardDao
import com.flower.server.database.dao.UserDao
import com.flower.server.repository.UseCase
import com.flower.server.repository.getUser
import com.flower.server.web.models.request.bonus_club.AddBonusCardRequest
import com.flower.server.web.models.response.bonus_club.AddBonusCardResponse
import com.flower.server.web.models.response.bonus_club.BonusCard
import java.rmi.ServerException

class AddBonusCardUseCase(private val bonusCardDao: BonusCardDao,
                          private val userDao: UserDao,
                          private val iLevelCheckCompositor: ILevelCheckCompositor,
                          private val addBonusCountUseCase : AddBonusCountUseCase) : UseCase<AddBonusCardRequest, AddBonusCardResponse> {

    override suspend fun getResponse(request: AddBonusCardRequest, token: String?): AddBonusCardResponse{
        val user = getUser(userDao, token)

        return iLevelCheckCompositor.check(user.level){
            val card = bonusCardDao.addBonusCard(
                name = request.name ?: user.login,
                active = request.active
            ) ?: throw ServerException("can't add card name=${request.name}")
            val count = addBonusCountUseCase.execute(card.id, 0.0)
            AddBonusCardResponse(BonusCard(id = card.id, name = card.name, active = card.active, count = count.bonus))
        }
    }
}