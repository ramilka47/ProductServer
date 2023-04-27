package com.flower.server.web.models.response.general

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.general.ReserveProductRequest

data class ReserveProductResponse(val generalOperation: GeneralOperation) : IResponse<ReserveProductRequest>