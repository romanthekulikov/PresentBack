package com.present_app

import com.present_app.plugins.configureRouting
import com.present_app.plugins.configureSecurity
import com.present_app.plugins.configureSerialization
import com.present_app.plugins.configureSockets
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT").toInt(), module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSecurity()
    configureSockets()
    configureRouting()
    configureSerialization()
}
