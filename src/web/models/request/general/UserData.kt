package com.flower.server.web.models.request.general

data class UserData(

    val name : String,
    val phone : String? = null,
    val address : String? = null)