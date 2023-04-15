package com.flower.server.web.models.request.catalog

import com.flower.server.web.models.IRequest

data class UpdateProductRequest(val id : Long,
                                val name : String,
                                val description : String? = null,
                                val photo : String? = null,
                                val producer : String? = null,
                                val gallery : List<String>) : IRequest
