package com.flower.server.web.models.response.user

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.user.UpdateUserNameRequest

data class UpdateUserNameResponse(val id : Long, val name : String) : IResponse<UpdateUserNameRequest>