package com.flower.server.plugins.interactors

import com.flower.server.helper.*
import com.flower.server.helper.execeptions.ExistException
import com.flower.server.models.request.IdRequest
import com.flower.server.models.response.CancelChequeResponse
import com.google.gson.JsonArray

object PostCancelSellProductInteractor : Interactor<IdRequest, CancelChequeResponse> {

    override suspend fun getResponse(request: IdRequest): CancelChequeResponse {
        val cheque = daoChequeGetter.cheque(request.id.toLong()) ?: throw ExistException("cheque is not exists")
        val sellingIds = gson.fromJson(cheque.sellings, JsonArray::class.java).map {
            it.asLong
        }
        val sellings = daoSellingGetter.allSellingByIds(sellingIds)

        daoChequeSetter.updateChequeCanceled(cheque.id, false)
        sellings.forEach {selling ->
            daoProductsSetter.updateCountProduct(
                selling.product.id.toLong(),
                selling.product.count + selling.count
            )
        }

        return CancelChequeResponse()
    }

}