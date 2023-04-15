package com.flower.server.web.models.request.user

import com.flower.server.web.models.IRequest

data class UpdatePasswordRequest(val id : Long, val newPassword : String) : IRequest