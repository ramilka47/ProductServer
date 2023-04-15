package com.flower.server.web.models.response.user

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.user.UpdatePasswordRequest

data class UpdatePasswordResponse(val id : Long, val password : String) : IResponse<UpdatePasswordRequest>
