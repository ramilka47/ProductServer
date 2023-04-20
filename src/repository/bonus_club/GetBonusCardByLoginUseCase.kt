package com.flower.server.repository.bonus_club

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.core.level_checker.check
import com.flower.server.database.dao.BonusCardDao
import com.flower.server.database.dao.UserDao
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.repository.UseCase
import com.flower.server.repository.getUser
import com.flower.server.web.models.request.bonus_club.GetBonusCardByLoginRequest
import com.flower.server.web.models.response.bonus_club.BonusCard
import com.flower.server.web.models.response.bonus_club.GetBonusCardByLoginResponse
import java.rmi.ServerException

class GetBonusCardByLoginUseCase(private val bonusCardDao: BonusCardDao,
                                 private val userDao: UserDao,
                                 private val iLevelCheckCompositor: ILevelCheckCompositor? = null,
                                 private val getBonusCountUseCase: GetBonusCountUseCase
)
    : UseCase<GetBonusCardByLoginRequest, GetBonusCardByLoginResponse> {

    override suspend fun getResponse(request: GetBonusCardByLoginRequest, token: String?): GetBonusCardByLoginResponse{
        val consumer : suspend () -> GetBonusCardByLoginResponse = {
            val user = getUser(userDao, token)
            if (user.login != request.login){
                throw RequestException("bad request")
            }

            val bonusCard = bonusCardDao.getBonusCard(user.login) ?: throw RequestException("bonus card is null")
            val count = getBonusCountUseCase.execute(bonusCard.id)?.bonus ?: 0.0
            GetBonusCardByLoginResponse(
                BonusCard(
                    id = bonusCard.id,
                    name = bonusCard.name,
                    active = bonusCard.active,
                    count = count
                )
            )
        }

        return iLevelCheckCompositor?.check(getUser(userDao, token).level){
            val bonusCard = bonusCardDao.getBonusCard(request.login) ?: throw RequestException("bonus card is null")
            val count = getBonusCountUseCase.execute(bonusCard.id)?.bonus ?: 0.0
            GetBonusCardByLoginResponse(
                BonusCard(
                    id = bonusCard.id,
                    name = bonusCard.name,
                    active = bonusCard.active,
                    count = count
                )
            )
        } ?: consumer()
    }

}