package com.flower.server.web.router

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Application.Router() {
    routing {
        get(){
            call.receiveText()
        }
    }
}