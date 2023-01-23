package com.flower.server.plugins.interactors

import com.flower.server.helper.daoProductsGetter
import com.flower.server.helper.daoProductsSetter
import com.flower.server.helper.daoReceiptSetter
import com.flower.server.helper.getCurrentTimeInSec
import com.flower.server.models.request.OperationProductsRequest
import com.flower.server.models.response.PayProductResponse

object PostPayProductInteractor : Interactor<OperationProductsRequest, PayProductResponse> {

    override suspend fun getResponse(request: OperationProductsRequest): PayProductResponse {
        val currentTime = getCurrentTimeInSec()

        request.products.forEach { opr ->
            daoReceiptSetter.addReceipt(
                opr.id,
                "",
                currentTime,
                opr.count,
                request.seller,
                opr.price
            )?.id?.toLong() ?: return@forEach
        }

        val products = daoProductsGetter.productByIds(request.products.map { it.id })

        products.forEach {product ->
            daoProductsSetter.updateCountProduct(
                product.id.toLong(),
                product.count + (request.products.find{ it.id == product.id.toLong() }?.count ?: 0))
        }

        return PayProductResponse()
    }

}