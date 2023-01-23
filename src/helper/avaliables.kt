package com.flower.server.helper

import com.flower.server.helper.execeptions.TokenIsEmptyException
import com.flower.server.helper.execeptions.UserIsNotFound
import io.ktor.server.application.*

private const val USER_LEVEL = 0
private const val MODER_LEVEL = 1
private const val ADMIN_LEVEl = 2
private const val GOD_LEVEL = 3

suspend fun ApplicationCall.moderLevelActions(actionName : String, block : suspend ApplicationCall.()->Unit){
    val token = request.headers.get("token") ?: throw TokenIsEmptyException()
    val user = daoUserGetter.getUserByToken(token) ?: throw UserIsNotFound()
    user.levelAllow(MODER_LEVEL, actionName){
        block()
    }
}

suspend fun ApplicationCall.adminLevelActions(actionName : String, block : suspend ApplicationCall.()->Unit){
    val token = request.headers.get("token") ?: throw TokenIsEmptyException()
    val user = daoUserGetter.getUserByToken(token) ?: throw UserIsNotFound()
    user.levelAllow(ADMIN_LEVEl, actionName){
        block()
    }
}

suspend fun ApplicationCall.godLevelActions(actionName : String, block : suspend ApplicationCall.()->Unit){
    val token = request.headers.get("token") ?: throw TokenIsEmptyException()
    val user = daoUserGetter.getUserByToken(token) ?: throw UserIsNotFound()
    user.levelAllow(GOD_LEVEL, actionName){
        block()
    }
}

suspend fun ApplicationCall.routeAdmin(nameAction : String, block : suspend ApplicationCall.()->Unit){
    withUserAgent {
        adminLevelActions(nameAction, block)
    }
}

suspend fun ApplicationCall.routeGod(nameAction : String, block : suspend ApplicationCall.()->Unit){
    withUserAgent {
        godLevelActions(nameAction, block)
    }
}

suspend fun ApplicationCall.routeModer(nameAction : String, block : suspend ApplicationCall.()->Unit){
    withUserAgent {
        moderLevelActions(nameAction, block)
    }
}