package com.flower.server.web.models.request.bonus_club

import com.flower.server.web.models.IRequest

data class AddBonusCardRequest(val name : String? = null, val active : Boolean) : IRequest