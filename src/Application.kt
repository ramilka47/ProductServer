package com.flower.server

import com.flower.server.database.impl.DatabaseFactory
import com.flower.server.helper.daoUserSetter
import com.flower.server.helper.generator
import com.flower.server.helper.getCurrentTimeInSec
import com.flower.server.models.request.AddUserRequest
import com.flower.server.plugins.interactors.PostAddUserInteractor
import com.flower.server.plugins.route.configureRouting
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

    CoroutineScope(Dispatchers.IO).launch {
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
    }

    configureRouting()

    /*configureTemplating()*/

    /*install(Authentication) {
    }

    install(ContentNegotiation) {
        gson {
        }
    }*/

  /*  val client = HttpClient(Jetty) {
        install(HttpTimeout) {
        }
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
        install(Logging) {
            level = LogLevel.HEADERS
        }
        BrowserUserAgent() // install default browser-like user-agent
        // install(UserAgent) { agent = "some user agent" }
    }*/
    runBlocking {
        // Sample for making a HTTP Client request
        /*
        val message = client.post<JsonSampleClass> {
            url("http://127.0.0.1:8080/path/to/endpoint")
            contentType(ContentType.Application.Json)
            body = JsonSampleClass(hello = "world")
        }
        */
    }

}

data class JsonSampleClass(val hello: String)

