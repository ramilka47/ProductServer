package com.flower.server.plugins.interactors

import com.flower.server.models.IHttpResponse
import com.flower.server.models.IRequest
import com.flower.server.models.IResponse
import com.flower.server.models.request.EmptyRequest
import com.flower.server.models.request.IdRequest

interface Interactor<T : IRequest, R : IResponse<T>> : IHttpResponse<T, R> {
    suspend fun getResponse(request : T) : R
}

suspend fun <R : IResponse<EmptyRequest>> Interactor<EmptyRequest, R>.getResponseWithEmpty() : R{
    return getResponse(EmptyRequest())
}

suspend fun <R : IResponse<IdRequest>> Interactor<IdRequest, R>.getResponseWithId(id : String) : R{
    return getResponse(IdRequest(id))
}