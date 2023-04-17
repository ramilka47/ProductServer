package com.flower.server.web.models.response.stockroom

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.stockroom.UpdateStockroomDataRequest

data class UpdateStockroomDataResponse(val product : Product) : IResponse<UpdateStockroomDataRequest>