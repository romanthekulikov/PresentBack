package com.present_app.routing_cases

import com.present_app.repository.dao.MessageDao
import com.present_app.repository.dao_impl.MessageDaoImpl
import io.ktor.server.application.*
import io.ktor.server.response.*
import java.sql.Connection

class RespondMessagesBySubstringRouting(private val call: ApplicationCall, private val connection: Connection) {
    suspend fun respond() {
        val substring = call.parameters["substring"]!!
        val idChat = call.parameters["chat_id"]!!.toInt()

        val messageDao: MessageDao = MessageDaoImpl(connection = connection)
        val messages = messageDao.getBySubstring(substring, idChat)!!
        call.respond(messages)
    }
}