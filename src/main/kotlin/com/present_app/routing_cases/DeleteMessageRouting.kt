package com.present_app.routing_cases

import com.present_app.repository.dao_impl.MessageDaoImpl
import io.ktor.server.application.*
import java.sql.Connection

class DeleteMessageRouting(private val call: ApplicationCall, private val connection: Connection) {
    fun delete() {
        val idMessage = call.parameters["message_id"]!!.toInt()
        val messageDao = MessageDaoImpl(connection)
        try {
            messageDao.deleteItem(idMessage)
        } catch (e: Exception) {
            println(e.message)
        }

    }
}