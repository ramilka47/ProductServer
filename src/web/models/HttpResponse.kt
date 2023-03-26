package com.flower.server.web.models

class HttpResponse<I : IRequest, R : IResponse<I>>(val code : Int, val error : String? = null, val body : R? = null)