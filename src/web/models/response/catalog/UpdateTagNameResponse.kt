package com.flower.server.web.models.response.catalog

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.catalog.UpdateTagNameRequest

data class UpdateTagNameResponse(val id : Long, val name : String) : IResponse<UpdateTagNameRequest>