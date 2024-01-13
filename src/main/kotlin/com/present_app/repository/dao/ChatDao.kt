package com.present_app.repository.dao

import com.present_app.repository.entity.Chat

interface ChatDao : BaseDao<Chat, Int> {
    fun getLastItem(): Chat?
    fun updateChatSettings(chat: Chat)
}