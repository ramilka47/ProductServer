package com.flower.server.models.response

import com.flower.server.models.IResponse
import com.flower.server.models.request.EmptyRequest
import com.flower.server.models.result.ResultUser

data class AllUsersResponse(val users : List<ResultUser>) : IResponse<EmptyRequest>