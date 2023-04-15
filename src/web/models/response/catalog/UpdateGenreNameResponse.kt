package com.flower.server.web.models.response.catalog

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.catalog.UpdateGenreNameRequest

data class UpdateGenreNameResponse(val id : Long, val name : String) : IResponse<UpdateGenreNameRequest>