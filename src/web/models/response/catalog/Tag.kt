package com.flower.server.web.models.response.catalog

import com.flower.server.database.models.product_info.TagEntity

data class Tag(val id : Long, val name : String)

fun TagEntity.toTag() = Tag(id, name)