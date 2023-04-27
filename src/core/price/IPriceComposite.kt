package com.flower.server.core.price

import com.flower.server.web.models.response.general.ProductDataGeneral

interface IPriceComposite {

    suspend fun execute(list : List<ProductDataGeneral>) : Double
}