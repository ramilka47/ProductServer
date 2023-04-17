package com.flower.server.web.models.response.catalog

data class AddProduct(val id : Long,
                      val name : String,
                      val description : String? = null,
                      val photo : String? = null,
                      val producer : String? = null,
                      val gallery : List<String>,
                      val count : Int,
                      val price : Double,
                      val discount : Float,
                      val unicode : String)