package com.flower.server.web.models.request.catalog

import com.flower.server.web.models.IRequest

data class AddProductsIntoTagRequest(val tagId : Long, val productIds : List<Long>) : IRequest