package com.flower.server.models.request

import com.flower.server.models.IRequest

data class UpdateProductRequest(var id : Long?,
                                val name : String? = null,
                                val description : String? = null,
                                val photo : ByteArray? = null,
                                val count : Int? = null,
                                val price : Double? = null,
                                val code : String? = null,
                                val categoryId : Long? = null) : IRequest