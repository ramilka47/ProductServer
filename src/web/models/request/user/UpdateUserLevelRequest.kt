package com.flower.server.web.models.request.user

import com.flower.server.web.models.IRequest

data class UpdateUserLevelRequest(val id : Long, val newLevel : Int) : IRequest