package com.flower.server.web

import com.flower.server.helper.gson
import com.flower.server.repository.UseCase
import com.flower.server.web.models.HttpRequest
import com.flower.server.web.models.HttpResponse
import com.flower.server.web.models.IRequest
import com.flower.server.web.models.IResponse
import com.flower.server.web.models.request.EmptyRequest
import com.flower.server.web.models.request.IdRequest
import io.ktor.server.application.*
import io.ktor.server.request.*

suspend fun <I : IRequest, R : IResponse<I>> execute(request: HttpRequest<I>, useCase: UseCase<I, R>): HttpResponse<I, R> =
    try{
        HttpResponse(code = 200, body = useCase.getResponse(request = request.body, token = request.token))
    }catch (e : Exception){
        HttpResponse(code = 400, error = e.message)
    }

suspend inline fun <reified I : IRequest, R : IResponse<I>> ApplicationCall.response(useCase: UseCase<I, R>, converter : ApplicationCall.()->I) : R =
    useCase.getResponse(converter(), request.token())

suspend inline fun <R : IResponse<IdRequest>> ApplicationCall.responseById(useCase: UseCase<IdRequest, R>) : R =
    useCase.getResponse(convertId(), request.token())

suspend inline fun <R : IResponse<EmptyRequest>> ApplicationCall.responseByEmptyRequest(useCase: UseCase<EmptyRequest, R>) : R =
    useCase.getResponse(convertEmpty(), request.token())

suspend inline fun <reified I : IRequest> ApplicationCall.convert() : I {
    return gson.fromJson<I>(this.receiveText(), I::class.java)
}

suspend inline fun ApplicationCall.convertId() : IdRequest {
    return IdRequest(parameters.id()?.toLong() ?: throw ParameterException.ParameterIdException)
}
suspend inline fun ApplicationCall.convertEmpty() : EmptyRequest {
    return EmptyRequest
}