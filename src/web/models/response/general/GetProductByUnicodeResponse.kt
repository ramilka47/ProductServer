package com.flower.server.web.models.response.general

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.general.GetProductByUnicodeRequest
import com.flower.server.web.models.response.stockroom.Product

data class GetProductByUnicodeResponse(val product : Product) : IResponse<GetProductByUnicodeRequest>