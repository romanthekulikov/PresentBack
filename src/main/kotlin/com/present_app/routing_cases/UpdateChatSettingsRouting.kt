package com.present_app.routing_cases

import com.present_app.repository.dao.ChatDao
import com.present_app.repository.dao_impl.ChatDaoImpl
import com.present_app.repository.entity.Chat
import io.ktor.server.application.*
import java.sql.Connection

class UpdateChatSettingsRouting(private val call: ApplicationCall, private val connection: Connection) {
    fun update() {
        val bgColor = call.parameters["bg_color"]!!
        val bgImage = call.parameters["bg_image"]!!
        val textSize = call.parameters["text_size"]!!.toInt()
        val chatId = call.parameters["chat_id"]!!.toInt()

        val chat = Chat(chatId, bgColor, bgImage, textSize)
        val chatDao: ChatDao = ChatDaoImpl(connection)
        try {
            chatDao.updateChatSettings(chat)
        } catch (e: Exception) {
            println(e.message)
        }
    }
}