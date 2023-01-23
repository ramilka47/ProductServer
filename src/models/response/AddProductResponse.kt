package com.flower.server.models.response

import com.flower.server.models.IResponse
import com.flower.server.models.request.AddProductRequest
import com.flower.server.models.result.ProductResponse

class AddProductResponse(val product : ProductResponse) : IResponse<AddProductRequest>