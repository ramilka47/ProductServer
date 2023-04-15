package com.flower.server.web.models.request.user

import com.flower.server.web.models.IRequest

data class UpdateUserNameRequest(val id : Long, val newName : String) : IRequest