package com.flower.server.models.response

import com.flower.server.models.IResponse
import com.flower.server.models.request.EmptyRequest

class AllLossResponse(val losses : List<LossResponse>) : IResponse<EmptyRequest>