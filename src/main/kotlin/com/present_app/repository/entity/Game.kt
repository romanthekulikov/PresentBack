package com.present_app.repository.entity

data class Game(
    val id: Int = -1,
    val idAdmin: Int,
    val idUser: Int = -1,
    val enterKey: String,
    val idChat: Int,
    val date: String = ""
)
