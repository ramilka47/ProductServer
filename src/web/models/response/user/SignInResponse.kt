package com.flower.server.web.models.response.user

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.user.SignInRequest

data class SignInResponse(val id : Long, val name : String, val token : String) : IResponse<SignInRequest>