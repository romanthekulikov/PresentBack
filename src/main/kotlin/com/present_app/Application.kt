package com.present_app

import com.present_app.plugins.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*

fun main() {
//    embeddedServer(CIO, port = 8080, host = "0.0.0.0", module = Application::module)
//        .start(wait = true)
    embeddedServer(CIO, port = System.getenv("PORT").toInt(), module = Application::module)
//        .start(wait = true)
}

fun Application.module() {
    configureSecurity()
    configureRouting()
    configureSerialization()
}
