package com.flower.server.web.models.request.user

import com.flower.server.web.models.IRequest

data class SignUpRequest(val name : String,
                         val login : String,
                         val password : String) : IRequest