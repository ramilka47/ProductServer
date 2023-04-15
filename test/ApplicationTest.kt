package com.flower.server

import io.ktor.http.*
import io.ktor.client.*
import io.ktor.client.engine.jetty.*
import io.ktor.client.request.*
import io.ktor.server.testing.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.*
import org.junit.Test

class ApplicationTest {
    @Test
    fun testRoot() {
      /*  withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("HELLO WORLD!", response.content)
            }
        }*/
    }
}
