package com.flower.server.models.request

import com.flower.server.models.IRequest

data class OperationProductRequest(val id : Long, val count : Int, val price : Double) : IRequest