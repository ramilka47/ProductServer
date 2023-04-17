package com.flower.server.web.models.response.stockroom

import com.flower.server.database.models.storage.ProductCountEntity

data class ProductCount(val productId : Long, val count : Int)

fun ProductCountEntity.toProductCount() = ProductCount(
    productId = productId, count = count
)