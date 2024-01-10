package com.present_app.repository.dao_impl

import com.present_app.repository.dao.ChatDao
import com.present_app.repository.entity.Chat
import java.sql.Connection

class ChatDaoImpl(private val connection: Connection) : ChatDao {
    override fun getLastItem(): Chat? {
        return try {
            val result = connection.createStatement().executeQuery(
                "SELECT id_chat, background_color, background_image, text_size FROM public.chat ORDER BY id_chat DESC LIMIT 1"
            )
            if (result.next()) {
                val id = result.getInt(1)
                val bgColor = result.getString(2)
                val bgImage = result.getString(3)
                val textSize = result.getInt(4)

                Chat(id = id, bgColor = bgColor, bgImage = bgImage, textSize = textSize)
            } else {
                null
            }

        } catch (e: Exception) {
            null
        }
    }

    override fun create(item: Chat): Boolean {
        return try {
            connection.createStatement().executeQuery(
                "INSERT INTO public.chat (background_color, background_image, text_size) VALUES ( '${item.bgColor}', '${item.bgImage}', ${item.textSize} )"
            )

            true
        } catch (e: Exception) {
            false
        }
    }

    override fun getItem(filter: Int): Chat? {
        return try {
            val result = connection.createStatement().executeQuery(
                "SELECT id_chat, background_color, background_image, text_size FROM public.chat WHERE id_chat = $filter"
            )
            if (result.next()) {
                val id = result.getInt(1)
                val bgColor = result.getString(2)
                val bgImage = result.getString(3)
                val textSize = result.getInt(4)

                Chat(id = id, bgColor = bgColor, bgImage = bgImage, textSize = textSize)
            } else {
                null
            }

        } catch (e: Exception) {
            null
        }
    }

    override fun deleteItem(filter: Int): Boolean {
        TODO("Not yet implemented")
    }
}