package com.flower.server.core.price

import com.flower.server.web.models.response.general.ProductDataGeneral

object SalePriceComposite : IPriceComposite {

    override suspend fun execute(list : List<ProductDataGeneral>) : Double = list.map {
        val discount = it.discount
        it.price * it.count * if (discount == 0f) 1f else discount
    }.sumOf{ it }
}