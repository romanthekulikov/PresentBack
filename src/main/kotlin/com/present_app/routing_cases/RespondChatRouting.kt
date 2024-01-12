package com.present_app.routing_cases

import com.present_app.Utils
import com.present_app.repository.dao.ChatDao
import com.present_app.repository.dao_impl.ChatDaoImpl
import io.ktor.server.application.*
import io.ktor.server.response.*
import java.sql.Connection

class RespondChatRouting(private val call: ApplicationCall, private val  connection: Connection) {
    suspend fun respond() {
        val chatId = call.parameters["chat_id"]!!.toInt()
        val chatDao: ChatDao = ChatDaoImpl(connection = connection)
        val chat = chatDao.getItem(chatId)
        if (chat != null) {
            call.respond(chat)
        } else {
            call.respond(Utils.getErrorResponse(404, "Чат не найден"))
        }
    }
}