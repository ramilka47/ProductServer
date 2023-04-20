package com.flower.server.repository.bonus_club

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.core.level_checker.check
import com.flower.server.database.dao.BonusCardDao
import com.flower.server.database.dao.UserDao
import com.flower.server.repository.UseCase
import com.flower.server.repository.getUser
import com.flower.server.web.models.request.EmptyRequest
import com.flower.server.web.models.response.bonus_club.BonusCard
import com.flower.server.web.models.response.bonus_club.GetAllBonusCardResponse

class GetAllBonusCardUseCase(
    private val bonusCardDao: BonusCardDao,
    private val userDao: UserDao,
    private val iLevelCheckCompositor: ILevelCheckCompositor,
    private val getBonusCountUseCase: GetBonusCountUseCase
) : UseCase<EmptyRequest, GetAllBonusCardResponse> {

    override suspend fun getResponse(request: EmptyRequest, token: String?): GetAllBonusCardResponse =
        iLevelCheckCompositor.check(getUser(userDao, token).level){
            val cards = bonusCardDao.getAllBonusCards()
            GetAllBonusCardResponse(
                cards.map {
                    BonusCard(it.id, it.name, it.active, getBonusCountUseCase.execute(it.id)?.bonus ?: 0.0)
                }
            )
        }
}