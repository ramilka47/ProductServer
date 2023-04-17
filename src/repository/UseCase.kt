package com.flower.server.repository

import com.flower.server.database.dao.UserDao
import com.flower.server.database.models.admin.UserEntity
import com.flower.server.helper.execeptions.BadUserTokenException
import com.flower.server.web.models.IRequest
import com.flower.server.web.models.IResponse

interface UseCase<T : IRequest, R : IResponse<T>> {
    suspend fun getResponse(request: T, token : String? = null) : R
}

internal suspend fun <T : IRequest, R : IResponse<T>> UseCase<T, R>.getUser(userDao: UserDao, token: String?) : UserEntity {
    return userDao.getUserByToken(token ?: throw BadUserTokenException()) ?: throw BadUserTokenException()
}

/*
suspend fun <R : IResponse<EmptyRequest>> Interactor<EmptyRequest, R>.getResponseWithEmpty() : R{
    return getResponse(EmptyRequest())
}

suspend fun <R : IResponse<IdRequest>> Interactor<IdRequest, R>.getResponseWithId(id : String) : R{
    return getResponse(IdRequest(id))
}*/
