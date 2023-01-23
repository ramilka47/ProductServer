package com.flower.server.models.response

import com.flower.server.models.IResponse
import com.flower.server.models.request.EmptyRequest
import com.flower.server.models.result.ChequeResponse

data class AllChequesResponse(val cheques : List<ChequeResponse>) : IResponse<EmptyRequest>