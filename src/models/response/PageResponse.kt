package com.flower.server.models.result

import com.flower.server.models.IResponse
import com.flower.server.models.request.PageRequest

data class PageResponse<T>(val page : Int, val size : Int, val items : List<T>) : IResponse<PageRequest>