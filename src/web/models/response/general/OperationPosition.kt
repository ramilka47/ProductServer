package com.flower.server.web.models.response.general

import com.flower.server.database.models.storage.OperationPositionEntity

data class OperationPosition(val id : Long, val productId : Long, val count : Int)

fun OperationPositionEntity.toOperationPosition() = OperationPosition(
    id = id, productId = productId, count = count
)