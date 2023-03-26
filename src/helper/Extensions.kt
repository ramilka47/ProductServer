package com.flower.server.helper

import com.flower.server.helper.execeptions.ClientException
import com.flower.server.helper.execeptions.UserLevelNotAllowedThisAction
import com.flower.server.helper.Constants.predicateUserAgent
import com.flower.server.web.models.IRequest
import com.flower.server.web.models.IResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

suspend fun ApplicationCall.withError(block : suspend ApplicationCall.()->Unit) {
    try {
        block()
    } catch (e: ClientException) {
        respond(HttpStatusCode(400, "error"))
        e.log()
    } catch (e : Exception) {
        respond(HttpStatusCode(500, "error"))
        e.log()
    } finally {
    }
}

suspend fun ApplicationCall.withErrorWithInfo(block : suspend ApplicationCall.()->Unit) {
    try {
        block()
    } catch (e: ClientException) {
        respond(HttpStatusCode(400, "error"), e.message ?: "unknowable exception")
        e.logWithAgent()
    } catch (e : Exception) {
        respond(HttpStatusCode(500, "error"), e.message ?: "unknowable exception")
        e.logWithAgent()
    } finally {
    }
}

suspend fun ApplicationCall.withUserAgent(block: suspend ApplicationCall.() -> Unit){
    if (predicateUserAgent(request.userAgent())){
        withErrorWithInfo(block)
    } else {
        withError(block)
    }
}

suspend fun <T : IRequest, R : IResponse<T>>  ApplicationCall.respondOk(response : R){
    respond(HttpStatusCode(200, "ok"), gson.toJson(response))
}

suspend fun Exception.log(){
    System.out.println("LOG_error: $message")
}

suspend fun Exception.logWithAgent(){
    System.out.println("LOG_error: $message")
}