package com.present_app.repository.dao

import com.present_app.repository.entity.Game

interface GameDao : BaseDao<Game, String> {
    fun getLastItemByIdAdmin(idAdmin: Int): Game?
    fun getById(idGame: Int): Game?
    fun getAllGameByUserId(idAdmin: Int): List<Game>?
    fun updateUserAndDateByIdGame(idUser: Int, date: String, idAdmin: Int)
}