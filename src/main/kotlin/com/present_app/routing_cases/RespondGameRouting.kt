package com.present_app.routing_cases

import com.present_app.repository.dao.GameDao
import com.present_app.repository.dao_impl.GameDaoImpl
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable
import java.sql.Connection

class RespondGameRouting(private val call: ApplicationCall, private val connection: Connection) {
    suspend fun respond() {
        val idGame = call.parameters["game_id"]!!.toInt()

        val gameDao: GameDao = GameDaoImpl(connection = connection)
        val game = gameDao.getById(idGame)
        if (game != null) {
            try {
                call.respond(GameResponse(game.id, game.idAdmin, game.idUser, game.idChat, game.date))
            } catch (e: Exception) {
                println(e.message)
            }

        }
    }
    @Serializable
    data class GameResponse(
        val id: Int,
        val id_admin: Int,
        val id_user: Int,
        val id_chat: Int,
        val date: String
    )
}