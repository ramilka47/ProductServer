package com.flower.server.web.models.request.user

import com.flower.server.web.models.IRequest

class SignInRequest(val login : String, val password : String) : IRequest