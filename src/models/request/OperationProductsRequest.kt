package com.flower.server.models.request

import com.flower.server.models.IRequest

data class OperationProductsRequest(
    val price : Double,
    val products : List<OperationProductRequest>,
    val seller : String) : IRequest