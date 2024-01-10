package com.present_app.plugins

import com.present_app.repository.ConnectionProvider
import com.present_app.routing_cases.AuthRouting
import com.present_app.routing_cases.CreateGameRouting
import com.present_app.routing_cases.EnterTheGameRouting
import com.present_app.routing_cases.RegRouting
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.Database
import java.io.File

fun sendError() {
    TODO("Not yet implemented")
}

fun sendData() {
    TODO("Not yet implemented")
}

fun Application.configureRouting() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5433/present_app_db",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "s1210501139"
    )
    val connection = ConnectionProvider.getInstance()

    routing {
        File("./presents_images").mkdirs()
        File("./user_icons").mkdirs()
        File("./chat_images").mkdirs()
        staticFiles("/static", File("presents_images"))
        staticFiles("/static", File("user_icons"))
        staticFiles("/static", File("chat_images"))

        get("/get_present_image") {
            val image = call.parameters["image"]
            call.respondFile(File("./presents_images/$image"))
        }

        get("/get_user_icon") {
            val image = call.parameters["image"]
            call.respondFile(File("./user_icons/$image"))
        }

        get("/get_chat_image") {
            val image = call.parameters["image"]
            call.respondFile(File("./chat_images/$image"))
        }

        get("/auth") {
            AuthRouting(call = call, connection = connection).doAuth()
        }

        get("/enter_the_game") {
            EnterTheGameRouting(call = call, connection = connection).enter()
        }

        post("/reg") {
            RegRouting(call = call, connection = connection).doReg()
        }

        post("/create_game") {
            CreateGameRouting(call = call, connection = connection).createGame()
        }
    }

}
