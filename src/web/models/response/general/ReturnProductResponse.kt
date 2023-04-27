package com.flower.server.web.models.response.general

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.general.ReturnProductRequest

data class ReturnProductResponse(val generalOperation: GeneralOperation) : IResponse<ReturnProductRequest>