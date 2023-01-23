package com.flower.server.plugins.interactors

import com.flower.server.helper.*
import com.flower.server.helper.execeptions.ExistException
import com.flower.server.models.request.OperationProductsRequest
import com.flower.server.models.response.ChequeSellResponse
import com.flower.server.models.result.ChequeResponse

object PostSellProductInteractor : Interactor<OperationProductsRequest, ChequeSellResponse> {

    override suspend fun getResponse(request: OperationProductsRequest): ChequeSellResponse {
        val currentTime = getCurrentTimeInSec()
        val sellingIds = mutableListOf<Long>()

        request.products.forEach { opr ->
            sellingIds.add(
                daoSellingSetter.addSelling(
                    opr.id,
                    "",
                    currentTime,
                    opr.count,
                    request.seller,
                    opr.price
                )?.id?.toLong() ?: return@forEach
            )
        }

        val cheque = daoChequeSetter.addCheque(
            price = request.price,
            name = request.seller,
            date = currentTime,
            sellingIds = sellingIds
        ) ?: throw ExistException("cheque can't add")

        val products = daoProductsGetter.productByIds(request.products.map { it.id })

        products.forEach { product ->
            daoProductsSetter.updateCountProduct(
                product.id.toLong(),
                product.count - (request.products.find { it.id == product.id.toLong() }?.count ?: 0)
            )
        }

        return ChequeSellResponse(
            ChequeResponse(
                id = cheque.id,
                name = cheque.name,
                price = cheque.price,
                date = cheque.date,
                productNames = products.map { it.name },
                canceled = cheque.canceled
            )
        )
    }

}