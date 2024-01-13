package com.present_app.repository.dao

import com.present_app.repository.entity.Message

interface MessageDao: BaseDao<Message, Int> {
    fun updateText(text: String, id: Int)

    fun getBySubstring(substring: String, idChat: Int): List<Message>?

}