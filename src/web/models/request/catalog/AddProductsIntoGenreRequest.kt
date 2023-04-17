package com.flower.server.web.models.request.catalog

import com.flower.server.web.models.IRequest

data class AddProductsIntoGenreRequest(val genreId : Long, val productIds : List<Long>) : IRequest