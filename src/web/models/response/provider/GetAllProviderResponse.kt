package com.flower.server.web.models.response.provider

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.EmptyRequest

data class GetAllProviderResponse(val providers : List<Provider>) : IResponse<EmptyRequest>