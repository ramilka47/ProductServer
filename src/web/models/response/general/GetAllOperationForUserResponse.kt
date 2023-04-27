package com.flower.server.web.models.response.general

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.EmptyRequest

data class GetAllOperationForUserResponse(val operations: List<GeneralOperation>) : IResponse<EmptyRequest>