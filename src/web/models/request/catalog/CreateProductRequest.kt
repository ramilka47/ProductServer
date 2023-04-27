package com.flower.server.web.models.request.catalog

import com.flower.server.web.models.IRequest

data class CreateProductRequest(val name : String,
                                val description : String? = null,
                                val photo : String,
                                val producer : String? = null,
                                val gallery : List<String> = listOf(),
                                val price : Double? = null,
                                val salePrice : Double? = null,
                                val discount : Float? = null,
                                val unicode : String? = null
) : IRequest