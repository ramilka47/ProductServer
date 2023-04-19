package com.flower.server.web.models.request.provider

import com.flower.server.web.models.IRequest

data class AddProviderRequest(val name : String,
                              val address : String,
                              val description : String,
                              val phone : String) : IRequest