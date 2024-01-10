package com.present_app.repository.entity

data class Present(
    val id: Int = -1,
    val text: String,
    val idSender: Int,
    val image: String,
    val keyOpen: String,
    val link: String
)
