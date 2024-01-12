package com.present_app.repository.entity

import kotlinx.serialization.Serializable

@Serializable
data class Chat(
    val id: Int,
    val bgColor: String = "default",
    val bgImage: String = "default",
    val textSize: Int = 12
)
