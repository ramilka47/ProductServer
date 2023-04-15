package com.flower.server.web.models

class HttpRequest<T : IRequest>(val token : String, val body : T)