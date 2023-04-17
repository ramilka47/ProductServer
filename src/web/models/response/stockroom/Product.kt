package com.flower.server.web.models.response.stockroom

import com.flower.server.web.models.response.catalog.Genre
import com.flower.server.web.models.response.catalog.Tag

data class Product(val id : Long,
                   val name : String,
                   val description : String? = null,
                   val photo : String? = null,
                   val producer : String? = null,
                   val gallery : List<String>,
                   val genres : List<Genre>,
                   val tags : List<Tag>,
                   val count : Int = 0,
                   val unicode : String = "",
                   val price : Double? = null,
                   val discount : Float? = null)