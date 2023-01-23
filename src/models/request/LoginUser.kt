package com.flower.server.models.request

import com.flower.server.models.IRequest

data class LoginUser(val login : String,
                     val password : String) : IRequest