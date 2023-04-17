package com.flower.server.web.models.request.catalog

import com.flower.server.web.models.IRequest

data class GetProductByNameRequest(val string : String) : IRequest