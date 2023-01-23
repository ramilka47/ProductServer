package com.flower.server.models.response

import com.flower.server.models.IResponse
import com.flower.server.models.request.LoginUser

data class LoginResponse(val token : String) : IResponse<LoginUser>