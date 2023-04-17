package com.flower.server.web.models.response.crm

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.EmptyRequest

data class GetAllCustomersResponse(val customers : List<Customer>) : IResponse<EmptyRequest>