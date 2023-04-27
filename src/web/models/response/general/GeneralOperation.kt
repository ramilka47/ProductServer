package com.flower.server.web.models.response.general

import com.flower.server.database.models.storage.StorageOperationEntity
import com.flower.server.web.models.response.stockroom.Operation
import com.flower.server.web.models.response.stockroom.ProductOperation
import com.flower.server.web.models.response.stockroom.toOperation

class GeneralOperation(val id : Long,
                       val positions : List<OperationPosition>,
                       val operation : Operation,
                       val count : Int,
                       val price : Double,
                       val date : Long)

fun StorageOperationEntity.toGeneralOperation(positions : List<OperationPosition>) =
    GeneralOperation(
        id = id,
        positions = positions,
        operation = operation.toOperation(),
        count = count,
        price = price,
        date = date
    )

fun ProductOperation.toGeneralOperation(positions : List<OperationPosition>) =
    GeneralOperation(
        id = id,
        positions = positions,
        operation = operation,
        count = count,
        price = price,
        date = date
    )