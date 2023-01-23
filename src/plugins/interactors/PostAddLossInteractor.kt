package com.flower.server.plugins.interactors

import com.flower.server.helper.daoLossSetter
import com.flower.server.helper.daoProductsGetter
import com.flower.server.helper.daoProductsSetter
import com.flower.server.helper.getCurrentTimeInSec
import com.flower.server.models.request.OperationProductsRequest
import com.flower.server.models.response.AddedLossResponse

object PostAddLossInteractor : Interactor<OperationProductsRequest, AddedLossResponse> {

    override suspend fun getResponse(request: OperationProductsRequest): AddedLossResponse {
        val currentTime = getCurrentTimeInSec()
        val sellingIds = mutableListOf<Long>()

        request.products.forEach { opr->
            sellingIds.add(
                daoLossSetter.addLoss(
                    opr.id,
                    "",
                    currentTime,
                    opr.count,
                    request.seller,
                    opr.price
                )?.id?.toLong() ?: return@forEach
            )
        }

        val products = daoProductsGetter.productByIds(request.products.map { it.id })

        products.forEach {product ->
            daoProductsSetter.updateCountProduct(
                product.id.toLong(),
                product.count - (request.products.find{ it.id == product.id.toLong() }?.count ?: 0))
        }

        return AddedLossResponse()
    }

}