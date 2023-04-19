package com.flower.server.web.models.response.bonus_club

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.IdRequest

data class GetBonusCardResponse(val bonusCard: BonusCard) : IResponse<IdRequest>