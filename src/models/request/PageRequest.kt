package com.flower.server.models.request

import com.flower.server.models.IRequest
import com.flower.server.models.Sort

data class PageRequest(val page : Int, val sort: Sort) : IRequest