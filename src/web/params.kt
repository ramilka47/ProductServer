package com.flower.server.web

import io.ktor.http.*
import io.ktor.server.request.*

fun Headers.token() = get("token")

fun ApplicationRequest.token() = headers.token()

fun Parameters.id() = get("id")

sealed class ParameterException(parameter : String) : Exception("parameter \"$parameter\" is null"){
    object ParameterIdException : ParameterException("id")
}
