package com.flower.server.web.models.response.bonus_club

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.bonus_club.GetBonusCardByLoginRequest

data class GetBonusCardByLoginResponse(val bonusCard: BonusCard) : IResponse<GetBonusCardByLoginRequest>