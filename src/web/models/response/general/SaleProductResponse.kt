package com.flower.server.web.models.response.general

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.general.SaleProductRequest

data class SaleProductResponse(val generalOperation: GeneralOperation) : IResponse<SaleProductRequest>