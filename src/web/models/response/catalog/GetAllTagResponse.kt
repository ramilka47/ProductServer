package com.flower.server.web.models.response.catalog

import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.EmptyRequest

data class GetAllTagResponse(val tags : List<Tag>) : IResponse<EmptyRequest>