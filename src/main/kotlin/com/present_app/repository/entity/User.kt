package com.present_app.repository.entity

data class User(
    val id: Int = -1,
    val password: String,
    val email: String,
    val icon: String,
    val name: String
)
