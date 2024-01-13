package com.present_app.repository.dao_impl

import com.present_app.repository.dao.MessageDao
import com.present_app.repository.entity.Message
import java.sql.Connection

class MessageDaoImpl(private val connection: Connection): MessageDao {
    override fun updateText(text: String, id: Int) {
        try {
            connection.createStatement().executeQuery("UPDATE public.message SET text = '$text' WHERE id_message = $id")
        } catch (_: Exception) {
        }

    }

    override fun getBySubstring(substring: String, idChat: Int): List<Message>? {
        return try {
            val result = connection.createStatement().executeQuery("SELECT * FROM public.message " +
                    "WHERE id_chat = $idChat AND text LIKE '%$substring%' ORDER BY id_message DESC")
            val listMessage = mutableListOf<Message>()
            while (result.next()) {
                listMessage.add(Message(
                    id = result.getInt(1),
                    idSender = result.getInt(2),
                    idChat = result.getInt(3),
                    text = result.getString(4),
                    idReplay = result.getInt(5),
                    departureTime = result.getLong(6)
                )
                )
            }
            listMessage
        } catch (e: Exception) {
            null
        }
    }

    override fun create(item: Message): Boolean {
        return try {
            connection.createStatement().executeQuery( "INSERT INTO public.message " +
                    "( id_sender, id_chat, \"text\", id_replay, departure_time ) " +
                    "VALUES " +
                    "( ${item.idSender}, ${item.idChat}, '${item.text}', ${item.idReplay}, '${item.departureTime}' )")
            false
        } catch (e: Exception) {
            true
        }
    }

    override fun getItem(filter: Int): Message? {
        return try {
            val result = connection.createStatement().executeQuery("SELECT * FROM public.message WHERE id_message = $filter")
            if(result.next()) {
                return Message(
                    id = result.getInt(1),
                    idSender = result.getInt(2),
                    idChat = result.getInt(3),
                    text = result.getString(4),
                    idReplay = result.getInt(5),
                    departureTime = result.getLong(6)
                )
            }
            null
        } catch (e: Exception) {
            null
        }
    }

    override fun deleteItem(filter: Int): Boolean {
        return try {
            connection.createStatement().executeQuery("DELETE FROM public.message WHERE id_message = $filter")
            false
        } catch (e: Exception) {
            true
        }
    }
}