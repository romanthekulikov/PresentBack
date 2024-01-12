package com.present_app.repository.dao_impl

import com.present_app.repository.dao.GameDao
import com.present_app.repository.entity.Game
import java.sql.Connection

class GameDaoImpl(private val connection: Connection) : GameDao {
    override fun getLastItemByIdAdmin(idAdmin: Int): Game? {
        return try {
            val result = connection.createStatement().executeQuery(
                "SELECT id_game, id_admin, enter_key, id_chat FROM public.game " +
                        "WHERE id_admin = $idAdmin ORDER BY id_game DESC LIMIT 1"
            )
            if (result.next()) {
                val id = result.getInt(1)
                val idAdminGame = result.getInt(2)
                val enterKey = result.getString(3)
                val idChat = result.getInt(4)
                return Game(id = id, idAdmin = idAdminGame, enterKey = enterKey, idChat = idChat)
            }
            null
        } catch (e: Exception) {
            null
        }
    }

    override fun getAllGameByUserId(idAdmin: Int): List<Game>? {
        return try {
            val result = connection.createStatement().executeQuery(
                "SELECT * WHERE id_admin = $idAdmin"
            )
            val gameList = mutableListOf<Game>()
            while (result.next()) {
                val id = result.getInt(1)
                val idAdminGame = result.getInt(2)
                val enterKey = result.getString(4)
                val idChat = result.getInt(5)
                gameList.add(Game(id = id, idAdmin = idAdminGame, enterKey = enterKey, idChat = idChat))
            }
            gameList
        } catch (e: Exception) {
            null
        }
    }

    override fun updateUserAndDateByIdGame(idUser: Int, date: String, idGame: Int) {
        try {
            connection.createStatement().executeQuery("UPDATE game SET start_date = '$date', id_user = $idUser WHERE id_game = $idGame")
        } catch (_: Exception) {}
    }

    override fun create(item: Game): Boolean {
        return try {
            connection.createStatement().executeQuery(
                "INSERT INTO public.game (id_admin, enter_key, id_chat) " +
                        "VALUES ( ${item.idAdmin}, '${item.enterKey}', ${item.idChat} )"
            )

            false
        } catch (_: Exception) {
            true
        }
    }

    override fun getItem(key: String): Game? {
        return try {
            val result = connection.createStatement().executeQuery(
                "SELECT id_game, id_admin, id_user, enter_key, id_chat, start_date FROM public.game " +
                        "WHERE enter_key = '$key' ORDER BY id_game DESC LIMIT 1"
            )
            if (result.next()) {
                val id = result.getInt(1)
                val idAdminGame = result.getInt(2)
                val idUser = result.getInt(3)
                val enterKey = result.getString(4)
                val idChat = result.getInt(5)
                val date = result.getString(6)
                return Game(
                    id = id,
                    idAdmin = idAdminGame,
                    enterKey = enterKey,
                    idChat = idChat,
                    idUser = idUser,
                    date = date ?: ""
                )
            }
            null
        } catch (e: Exception) {
            null
        }
    }

    override fun deleteItem(filter: String): Boolean {
        TODO("Not yet implemented")
    }
}