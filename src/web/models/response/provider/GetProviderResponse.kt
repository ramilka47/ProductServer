package com.flower.server.web.models.response.provider

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.IdRequest

data class GetProviderResponse(val provider : Provider) : IResponse<IdRequest>