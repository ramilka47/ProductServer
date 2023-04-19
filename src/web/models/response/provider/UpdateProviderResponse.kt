package com.flower.server.web.models.response.provider

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.provider.UpdateProviderRequest

data class UpdateProviderResponse(val provider: Provider) : IResponse<UpdateProviderRequest>