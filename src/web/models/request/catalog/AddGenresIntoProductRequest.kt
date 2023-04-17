package com.flower.server.web.models.request.catalog

import com.flower.server.web.models.IRequest

data class AddGenresIntoProductRequest(val productId : Long, val genreIds : List<Long>) : IRequest