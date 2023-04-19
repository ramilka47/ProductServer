package com.flower.server.web.models.response.provider

import com.flower.server.database.models.provider.ProviderEntity

data class Provider(val id : Long,
                    val name : String,
                    val address : String,
                    val description : String,
                    val phone : String)

fun ProviderEntity.toProvider() = Provider(
    id = id,
    name = name,
    address = address,
    description = description,
    phone = phone
)