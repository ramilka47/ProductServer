package com.flower.server.plugins.route

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        RoutingUser()
        RoutingModer()
        RoutingAdmin()
        RoutingGod()
    }
}

