package com.flower.server.web.models.request.catalog

import com.flower.server.web.models.IRequest

data class AddTagsIntoProductRequest(val productId : Long, val tagIds : List<Long>) : IRequest