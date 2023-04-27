package com.flower.server.core.price

import com.flower.server.web.models.response.general.ProductDataGeneral

object PriceComposite : IPriceComposite {

    override suspend fun execute(list : List<ProductDataGeneral>) : Double = list.sumOf{ it.price * it.count }
}