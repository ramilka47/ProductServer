package com.flower.server.models.result

import com.flower.server.database.models.Product
import com.flower.server.models.IResponse
import com.flower.server.models.request.IdRequest

data class ProductResponse(
    val id : String,
    val name : String,
    val description : String,
    val photo : String,
    val count : Int,
    val price : Double,
    val code : String,
    val category : CategoryResponse?) : IResponse<IdRequest>

fun Product.toProductResponse(category: CategoryResponse? = null) =
    ProductResponse(
        id,
        name,
        description,
        photo,
        count,
        price,
        code,
        category
    )