package com.present_app.routing_cases

import com.present_app.repository.dao_impl.MessageDaoImpl
import com.present_app.repository.entity.Message
import io.ktor.server.application.*
import java.sql.Connection

class SaveMessageRouting(private val call: ApplicationCall, private val connection: Connection) {
    fun save() {
        val idSender = call.parameters["sender_id"]!!.toInt()
        val idChat = call.parameters["chat_id"]!!.toInt()
        val text = call.parameters["text"]!!
        val idReplay = call.parameters["replay_id"]?.toInt()

        val message = Message(
            idSender = idSender,
            idChat = idChat,
            text = text,
            idReplay = idReplay,
            departureTime = System.currentTimeMillis()
        )

        val messageDao = MessageDaoImpl(connection)
        try {
            messageDao.create(message)
        } catch (e: Exception) {
            println(e.message)
        }

    }
}