package com.flower.server.models.response

import com.flower.server.models.IResponse
import com.flower.server.models.request.OperationProductsRequest
import com.flower.server.models.result.ChequeResponse

class ChequeSellResponse(val chequeResponse: ChequeResponse) : IResponse<OperationProductsRequest>