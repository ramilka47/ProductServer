package com.flower.server.web.models.response.provider

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.provider.AddProviderRequest

data class AddProviderResponse(val provider: Provider) : IResponse<AddProviderRequest>