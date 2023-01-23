package com.flower.server.models.request

import com.flower.server.models.IRequest

data class AddProductRequest(
    val name : String,
    val description : String,
    val photo : ByteArray? = null,
    val count : Int,
    val price : Double,
    val code : String,
    val categoryId : Long? = null) : IRequest