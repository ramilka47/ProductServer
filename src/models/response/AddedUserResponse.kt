package com.flower.server.models.response

import com.flower.server.models.IResponse
import com.flower.server.models.request.AddUserRequest

data class AddedUserResponse(val token : String) : IResponse<AddUserRequest>