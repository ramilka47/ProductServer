package com.flower.server.web.models.response.general

data class GeneralCustomer(val id : Long,
                           val name : String?,
                           val phone : String?,
                           val address : String?,
                           val products : List<GeneralOperation>)