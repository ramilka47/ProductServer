package com.flower.server.web.models.response.stockroom

import com.flower.server.database.models.storage.StorageOperationEntity
import com.flower.server.database.models.storage.StorageOperationEnum

data class ProductOperation(val id : Long,
                            val productOperationIds : List<Long>,
                            val operation : Operation,
                            val count : Int,
                            val price : Double,
                            val date : Long)

enum class Operation{
    BUY, SALE, WRITE_OFF, RETURN, RESERVE;
}

internal fun StorageOperationEntity.toProductOperation() = ProductOperation(
    id = id, productOperationIds = operationPositions, operation = operation.toOperation(), count = count, price = price, date = date
)

internal fun StorageOperationEnum.toOperation() =
    when(this){
        StorageOperationEnum.BUY -> Operation.BUY
        StorageOperationEnum.SALE -> Operation.SALE
        StorageOperationEnum.WRITE_OFF -> Operation.WRITE_OFF
        StorageOperationEnum.RETURN -> Operation.RETURN
        StorageOperationEnum.RESERVE -> Operation.RESERVE
    }