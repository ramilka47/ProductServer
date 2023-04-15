package com.flower.server.web.models.response.catalog

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.catalog.CreateGenreRequest

data class CreateGenreResponse(val id : Long, val name : String) : IResponse<CreateGenreRequest>
