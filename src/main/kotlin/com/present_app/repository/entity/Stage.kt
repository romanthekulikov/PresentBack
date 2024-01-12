package com.present_app.repository.entity

data class Stage(
    val id: Int = -1,
    val textStage: String,
    val hintText: String,
    val long: Double,
    val lat: Double,
    val idGame: Int,
    val key_present_game: String,
    val idPresent: Int,
    var is_done: Boolean = false
)
