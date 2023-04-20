package com.flower.server.repository.bonus_club

import com.flower.server.database.dao.BonusCardDao
import com.flower.server.database.dao.UserDao
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.repository.UseCase
import com.flower.server.repository.getUser
import com.flower.server.web.models.request.EmptyRequest
import com.flower.server.web.models.response.bonus_club.BonusCard
import com.flower.server.web.models.response.bonus_club.GetBonusCardResponse

class GetBonusCardUseCase(private val bonusCardDao: BonusCardDao,
                          private val userDao: UserDao,
                          private val getBonusCountUseCase: GetBonusCountUseCase) :
    UseCase<EmptyRequest, GetBonusCardResponse> {

    override suspend fun getResponse(request: EmptyRequest, token: String?): GetBonusCardResponse {
        val user = getUser(userDao, token)
        val bonusCard = bonusCardDao.getBonusCard(user.login) ?: throw RequestException("bonus card is null")
        val count = getBonusCountUseCase.execute(bonusCard.id)?.bonus ?: 0.0

        return GetBonusCardResponse(
            BonusCard(
                id = bonusCard.id,
                name = bonusCard.name,
                active = bonusCard.active,
                count = count
            )
        )
    }
}