package com.flower.server.web.models.response.bonus_club

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.EmptyRequest

data class GetAllBonusCardResponse(val bonusCards : List<BonusCard>) : IResponse<EmptyRequest>