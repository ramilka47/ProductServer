package com.flower.server.web.models.response.general

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.general.PayProductRequest

data class PayProductResponse(val generalOperation: GeneralOperation) : IResponse<PayProductRequest>