package com.present_app.repository.entity

data class Message(
    val id: Int? = -1,
    val idSender: Int,
    val idChat: Int,
    val text: String,
    val idReplay: Int?,
    val departureTime: Long
)