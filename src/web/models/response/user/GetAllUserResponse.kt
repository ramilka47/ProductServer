package com.flower.server.web.models.response.user

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.EmptyRequest

data class GetAllUserResponse(val users : List<User>) : IResponse<EmptyRequest>