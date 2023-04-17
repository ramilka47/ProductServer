package com.flower.server.web.models.response.crm

import com.flower.server.database.models.crm.CustomerEntity

data class Customer(val id : Long,
                    val name : String?,
                    val phone : String?,
                    val address : String?)

fun CustomerEntity.toCustomer() = Customer(
    id = id, name = name, phone = phone, address = address
)