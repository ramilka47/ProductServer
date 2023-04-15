package com.flower.server.web.models.response.user

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.user.SignUpRequest

data class SignUpResponse(val token : String) : IResponse<SignUpRequest>