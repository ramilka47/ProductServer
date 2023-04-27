package com.flower.server.web.models.response.general

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.general.WriteOffProductRequest

data class WriteOffProductResponse(val generalOperation: GeneralOperation) : IResponse<WriteOffProductRequest>