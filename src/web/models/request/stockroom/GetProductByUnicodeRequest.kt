package com.flower.server.web.models.request.stockroom

import com.flower.server.web.models.IRequest

data class GetProductByUnicodeRequest(val unicode : String) : IRequest