package com.flower.server

import com.flower.server.database_layer.database.impl.DatabaseFactory
import com.flower.server.web.router.Router
import io.ktor.client.request.*
import kotlinx.coroutines.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>){/*: Unit = io.ktor.server.netty.EngineMain.main(args)*/
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

@Suppress("unused") // Referenced in application.conf
fun Application.module(testing : Boolean = false) {
    DatabaseFactory.init()

  /*  CoroutineScope(Dispatchers.IO).launch {
        try {
            val userRequest = AddUserRequest(
                "Ramil",
                "ramil",
                "qwerty",
                3
            )
            val response = PostAddUserInteractor.getResponse(userRequest)
            System.out.println("LOG_debug: user added = ${userRequest} id=${response.token}")
        } catch (e : Exception){
            System.out.println("LOG_debug: user not added")
        }
    }*/

    Router()
}

data class JsonSampleClass(val hello: String)

