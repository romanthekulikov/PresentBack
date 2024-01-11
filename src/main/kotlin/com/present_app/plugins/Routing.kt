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
//    Database.connect(
//        url = "jdbc:postgresql://localhost:5433/present_app_db",
//        driver = "org.postgresql.Driver",
//        user = "postgres",
//        password = "s1210501139"
//    )
    Database.connect(
        url = "jdbc:postgresql://monorail.proxy.rlwy.net:25822/railway",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "3adCFd6ceG2-gBA*2g13*4DB43dC-2eD"
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

//        post("create_db") {
//            connection.createStatement().execute("BEGIN;\n" +
//                    "\n" +
//                    "\n" +
//                    "CREATE TABLE IF NOT EXISTS public.\"user\"\n" +
//                    "(\n" +
//                    "    id_user serial PRIMARY KEY,\n" +
//                    "    password VARCHAR(25) NOT NULL,\n" +
//                    "    email VARCHAR(25) NOT NULL,\n" +
//                    "    name VARCHAR(25),\n" +
//                    "    icon VARCHAR(255)\n" +
//                    ");\n" +
//                    "\n" +
//                    "CREATE TABLE IF NOT EXISTS public.game\n" +
//                    "(\n" +
//                    "    id_game serial PRIMARY KEY,\n" +
//                    "    id_admin integer NOT NULL,\n" +
//                    "    id_user integer,\n" +
//                    "    enter_key VARCHAR(12) NOT NULL,\n" +
//                    "    id_chat integer NOT NULL,\n" +
//                    "    start_date date\n" +
//                    ");\n" +
//                    "\n" +
//                    "CREATE TABLE IF NOT EXISTS public.chat\n" +
//                    "(\n" +
//                    "    id_chat serial PRIMARY KEY,\n" +
//                    "    background_color VARCHAR(10),\n" +
//                    "    background_image VARCHAR(150),\n" +
//                    "    text_size integer\n" +
//                    ");\n" +
//                    "\n" +
//                    "CREATE TABLE IF NOT EXISTS public.message\n" +
//                    "(\n" +
//                    "    id_message serial PRIMARY KEY,\n" +
//                    "    id_sender integer NOT NULL,\n" +
//                    "    id_chat integer NOT NULL,\n" +
//                    "    text VARCHAR(255),\n" +
//                    "    id_replay integer,\n" +
//                    "    departure_time bigint NOT NULL\n" +
//                    ");\n" +
//                    "\n" +
//                    "CREATE TABLE IF NOT EXISTS public.stage\n" +
//                    "(\n" +
//                    "    id_stage serial PRIMARY KEY,\n" +
//                    "    text_stage VARCHAR(255) NOT NULL,\n" +
//                    "    hint_text VARCHAR(255) NOT NULL,\n" +
//                    "    \"long\" double precision NOT NULL,\n" +
//                    "    lat double precision NOT NULL,\n" +
//                    "    id_game integer NOT NULL,\n" +
//                    "    id_present integer NOT NULL,\n" +
//                    "\tUNIQUE (id_present)\n" +
//                    ");\n" +
//                    "\n" +
//                    "CREATE TABLE IF NOT EXISTS public.present\n" +
//                    "(\n" +
//                    "    id_present serial PRIMARY KEY,\n" +
//                    "    text VARCHAR(150) NOT NULL,\n" +
//                    "    id_sender integer NOT NULL,\n" +
//                    "    present_img VARCHAR(150),\n" +
//                    "    key VARCHAR(255) NOT NULL,\n" +
//                    "    redirect_url VARCHAR(255) NOT NULL\n" +
//                    ");\n" +
//                    "\n" +
//                    "CREATE TABLE IF NOT EXISTS public.push_notification\n" +
//                    "(\n" +
//                    "    id_push serial PRIMARY KEY,\n" +
//                    "    id_user integer NOT NULL,\n" +
//                    "    text VARCHAR(150) NOT NULL,\n" +
//                    "    is_send boolean NOT NULL\n" +
//                    ");\n" +
//                    "\n" +
//                    "ALTER TABLE IF EXISTS public.game\n" +
//                    "    ADD FOREIGN KEY (id_admin)\n" +
//                    "    REFERENCES public.\"user\" (id_user) MATCH SIMPLE\n" +
//                    "    ON UPDATE NO ACTION\n" +
//                    "    ON DELETE NO ACTION\n" +
//                    "    NOT VALID;\n" +
//                    "\n" +
//                    "\n" +
//                    "ALTER TABLE IF EXISTS public.game\n" +
//                    "    ADD FOREIGN KEY (id_chat)\n" +
//                    "    REFERENCES public.chat (id_chat) MATCH SIMPLE\n" +
//                    "    ON UPDATE NO ACTION\n" +
//                    "    ON DELETE NO ACTION\n" +
//                    "    NOT VALID;\n" +
//                    "\n" +
//                    "\n" +
//                    "ALTER TABLE IF EXISTS public.message\n" +
//                    "    ADD FOREIGN KEY (id_chat)\n" +
//                    "    REFERENCES public.chat (id_chat) MATCH SIMPLE\n" +
//                    "    ON UPDATE NO ACTION\n" +
//                    "    ON DELETE NO ACTION\n" +
//                    "    NOT VALID;\n" +
//                    "\n" +
//                    "\n" +
//                    "ALTER TABLE IF EXISTS public.stage\n" +
//                    "    ADD FOREIGN KEY (id_game)\n" +
//                    "    REFERENCES public.game (id_game) MATCH SIMPLE\n" +
//                    "    ON UPDATE NO ACTION\n" +
//                    "    ON DELETE NO ACTION\n" +
//                    "    NOT VALID;\n" +
//                    "\n" +
//                    "\n" +
//                    "ALTER TABLE IF EXISTS public.stage\n" +
//                    "    ADD FOREIGN KEY (id_present)\n" +
//                    "    REFERENCES public.present (id_present) MATCH SIMPLE\n" +
//                    "    ON UPDATE NO ACTION\n" +
//                    "    ON DELETE NO ACTION\n" +
//                    "    NOT VALID;\n" +
//                    "\n" +
//                    "\n" +
//                    "ALTER TABLE IF EXISTS public.push_notification\n" +
//                    "    ADD FOREIGN KEY (id_user)\n" +
//                    "    REFERENCES public.\"user\" (id_user) MATCH SIMPLE\n" +
//                    "    ON UPDATE NO ACTION\n" +
//                    "    ON DELETE NO ACTION\n" +
//                    "    NOT VALID;\n" +
//                    "\n" +
//                    "END;")
//        }
    }
}
