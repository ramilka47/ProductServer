package com.flower.server.web.models.response.catalog

import com.flower.server.database.models.product_info.ProductEntity

class Product(val id : Long,
              val name : String,
              val description : String? = null,
              val photo : String? = null,
              val producer : String? = null,
              val gallery : List<String>)

fun ProductEntity.toProduct() = Product(
    id = id,
    name = name,
    description = description,
    photo = photo,
    producer = producer,
    gallery = gallery)