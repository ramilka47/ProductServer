package com.flower.server.web.models.response.catalog

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.catalog.DeleteGenreRequest

data class DeleteGenreResponse(val success : Boolean) : IResponse<DeleteGenreRequest>
