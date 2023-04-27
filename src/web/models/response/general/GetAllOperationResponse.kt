package com.flower.server.web.models.response.general

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.EmptyRequest

data class GetAllOperationResponse(val operations: List<GeneralCustomer>) : IResponse<EmptyRequest>