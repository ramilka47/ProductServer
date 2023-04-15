package com.flower.server.web.models.request.catalog

import com.flower.server.web.models.IRequest

data class UpdateGenreNameRequest(val id : Long, val newName : String) : IRequest