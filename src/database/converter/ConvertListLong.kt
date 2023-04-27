package com.flower.server.database.converter

import com.flower.server.helper.gson
import com.google.gson.JsonArray

object ConvertListLong {

    fun toList(string: String) : List<Long> =
        gson.fromJson(string, JsonArray::class.java).map { it.asLong }

    fun toString(list : List<Long>) : String =
        gson.toJson(list)

}