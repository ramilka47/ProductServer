package com.flower.server.web.models.response.catalog

import com.flower.server.database.models.product_info.GenreEntity

data class Genre(val id : Long, val name : String)

fun GenreEntity.toGenre() = Genre(id, name)