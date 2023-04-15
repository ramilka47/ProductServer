package com.flower.server.web.models.response.catalog

data class ProductInfo(val id : Long,
                       val name : String,
                       val description : String? = null,
                       val photo : String? = null,
                       val producer : String? = null,
                       val gallery : List<String>,
                       val genres : List<Genre>,
                       val tags : List<Tag>)