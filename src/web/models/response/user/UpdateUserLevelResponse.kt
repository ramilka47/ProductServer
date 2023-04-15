package com.flower.server.web.models.response.user

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.user.UpdateUserLevelRequest

data class UpdateUserLevelResponse(val id : Long, val login : String, val level : Int) : IResponse<UpdateUserLevelRequest>