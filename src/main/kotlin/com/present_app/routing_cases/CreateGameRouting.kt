package com.present_app.routing_cases

import com.present_app.repository.dao.ChatDao
import com.present_app.repository.dao.GameDao
import com.present_app.repository.dao.PresentDao
import com.present_app.repository.dao.StageDao
import com.present_app.repository.dao_impl.ChatDaoImpl
import com.present_app.repository.dao_impl.GameDaoImpl
import com.present_app.repository.dao_impl.PresentDaoImpl
import com.present_app.repository.dao_impl.StageDaoImpl
import com.present_app.repository.entity.Chat
import com.present_app.repository.entity.Game
import com.present_app.repository.entity.Present
import com.present_app.repository.entity.Stage
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.sql.Connection
import java.util.*

class CreateGameRouting(private val call: ApplicationCall, private val connection: Connection) {
    suspend fun createGame() {
        val json = call.parameters["json"]
        if (json != null) {
            try {
                val response = JSONObject(json)
                val gameSet = jsonToGame(json = response)
                val openKey = (0..9999999).random().toString() + gameSet.idAdmin.toString()
                val createChat = Chat(id = -1)
                val chatDao: ChatDao = ChatDaoImpl(connection = connection)
                chatDao.create(createChat)
                val dbChat = chatDao.getLastItem()
                val idChat = dbChat!!.id
                val game = Game(idAdmin = gameSet.idAdmin, enterKey = openKey, idChat = idChat)
                val gameDao: GameDao = GameDaoImpl(connection = connection)
                gameDao.create(game)
                val dbGame = gameDao.getLastItemByIdAdmin(idAdmin = gameSet.idAdmin)
                val presentDao: PresentDao = PresentDaoImpl(connection = connection)
                if (gameSet.presents.isNotEmpty() && dbGame != null) {
                    for (i in 0..<gameSet.presents.size) {
                        val present = gameSet.presents[i]
                        val byteArray = present.img.toByteArray()
                        val imageName = UUID.randomUUID().toString()
                        val file = File("./presents_images/$imageName.png")
                        val outStream = FileOutputStream(file)
                        outStream.write(byteArray)
                        outStream.flush()
                        outStream.close()
                        val dbPresent = Present(
                            text = present.text,
                            idSender = gameSet.idAdmin,
                            image = imageName,
                            keyOpen = present.key,
                            link = present.link
                        )
                        presentDao.create(dbPresent)
                        val newPresent = presentDao.getLastItemBySenderId(dbPresent.idSender)
                        if (newPresent != null) {
                            val idPresent = newPresent.id
                            val stageDao: StageDao = StageDaoImpl(connection = connection)
                            val stage = gameSet.stages[i]
                            val dbStage = Stage(
                                textStage = stage.text,
                                hintText = stage.hint,
                                long = stage.long,
                                lat = stage.lat,
                                idGame = dbGame.id,
                                idPresent = idPresent
                            )
                            stageDao.create(dbStage)
                        }

                    }
                    val callResponse = Response(game_id = dbGame.id, key_enter = openKey)
                    call.respond(callResponse)
                }
            } catch (_: Exception) {
            }
        }

    }

    private fun jsonToGame(json: JSONObject): GameResponse {
        val id = json.getInt("id_admin")
        val stagesJson = json.getJSONArray("stages")
        val presentsJson = json.getJSONArray("presents")
        val stages: MutableList<Stage> = mutableListOf()
        val presents: MutableList<Present> = mutableListOf()
        for (i in 0..<stagesJson.length()) {
            val item = stagesJson.getJSONObject(i)
            val stage = Stage(
                text = item.getString("text_stage"),
                hint = item.getString("hint_text"),
                long = item.getDouble("long"),
                lat = item.getDouble("lat")
            )
            stages.add(stage)
        }

        for (i in 0..<presentsJson.length()) {
            val item = presentsJson.getJSONObject(i)
            val present = Present(
                text = item.getString("present_text"),
                img = item.getString("present_img"),
                key = item.getString("key"),
                link = item.getString("redirect_link")
            )
            presents.add(present)
        }

        return GameResponse(idAdmin = id, stages = stages, presents = presents)
    }

    private data class GameResponse(
        val idAdmin: Int,
        val stages: List<Stage>,
        val presents: List<Present>
    )

    private data class Stage(
        val text: String,
        val hint: String,
        val long: Double,
        val lat: Double
    )

    private data class Present(
        val text: String,
        val img: String,
        val key: String,
        val link: String
    )

    @Serializable
    private data class Response(
        val game_id: Int,
        val key_enter: String
    )
}