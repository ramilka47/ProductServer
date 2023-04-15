package com.flower.server.web.models.response.catalog

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.catalog.CreateTagRequest

data class CreateTagResponse(val id : Long, val name : String) : IResponse<CreateTagRequest>