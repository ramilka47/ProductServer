package com.flower.server.models.response

import com.flower.server.models.IResponse
import com.flower.server.models.request.EmptyRequest

data class AllSellingResponse(val sellings : List<SellingResponse>) : IResponse<EmptyRequest>