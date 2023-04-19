package com.flower.server.web.models.response.provider

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.provider.DeleteProviderRequest

data class DeleteProviderResponse(val success : Boolean) : IResponse<DeleteProviderRequest>