package com.present_app.routing_cases

import com.present_app.repository.dao.PresentDao
import com.present_app.repository.dao_impl.PresentDaoImpl
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable
import java.sql.Connection

class OpenPresentRouting(private val call: ApplicationCall, private val connection: Connection) {
    suspend fun open() {
        val idPresent = call.parameters["present_id"]!!.toInt()
        val presentDao: PresentDao = PresentDaoImpl(connection = connection)
        val present = presentDao.getItem(idPresent)!!

        call.respond(OpenPresentRespond(present.keyOpen))
    }

    @Serializable
    data class OpenPresentRespond(
        val key_open: String
    )
}