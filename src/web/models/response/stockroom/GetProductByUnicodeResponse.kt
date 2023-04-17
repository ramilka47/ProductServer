package com.flower.server.web.models.response.stockroom

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.stockroom.GetProductByUnicodeRequest

data class GetProductByUnicodeResponse(val product : Product) : IResponse<GetProductByUnicodeRequest>