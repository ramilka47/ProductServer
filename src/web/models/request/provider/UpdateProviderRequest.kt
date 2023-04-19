package com.flower.server.web.models.request.provider

import com.flower.server.web.models.IRequest

data class UpdateProviderRequest(val id : Long,
                                 val name : String? = null,
                                 val address : String? = null,
                                 val description : String? = null,
                                 val phone : String? = null) : IRequest