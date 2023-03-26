package com.flower.server.repository

import com.flower.server.web.models.IRequest
import com.flower.server.web.models.IResponse

interface Interactor<T : IRequest, R : IResponse<T>> {
    suspend fun getResponse(request: IRequest, token : String? = null) : R
}

/*
suspend fun <R : IResponse<EmptyRequest>> Interactor<EmptyRequest, R>.getResponseWithEmpty() : R{
    return getResponse(EmptyRequest())
}

suspend fun <R : IResponse<IdRequest>> Interactor<IdRequest, R>.getResponseWithId(id : String) : R{
    return getResponse(IdRequest(id))
}*/
