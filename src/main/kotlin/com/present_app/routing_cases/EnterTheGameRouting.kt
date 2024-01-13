package com.present_app.routing_cases

import com.present_app.Utils
import com.present_app.repository.dao.GameDao
import com.present_app.repository.dao.PresentDao
import com.present_app.repository.dao.StageDao
import com.present_app.repository.dao_impl.GameDaoImpl
import com.present_app.repository.dao_impl.PresentDaoImpl
import com.present_app.repository.dao_impl.StageDaoImpl
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable
import java.sql.Connection
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EnterTheGameRouting(private val call: ApplicationCall, private val connection: Connection) {
    suspend fun enter() {
        val idUser = call.parameters["id_user"]?.toInt()
        val key = call.parameters["key"]
        if (key != null && idUser != null) {
            val gameDao: GameDao = GameDaoImpl(connection = connection)
            var game = gameDao.getItem(key)
            if (game != null) {
                if (idUser != game.idAdmin) {
                    val current = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    val date = current.format(formatter)
                    gameDao.updateUserAndDateByIdGame(idUser, date, game.id)
                    game = gameDao.getItem(key)!!
                }
                val stageDao: StageDao = StageDaoImpl(connection = connection)
                val presentDao: PresentDao = PresentDaoImpl(connection = connection)
                val stages = stageDao.getStageList(game.id)
                if (stages != null) {
                    val presents = presentDao.getPresentsListByStages(stages)
                    val stagesResponse = mutableListOf<EnterStageObject>()
                    val presentsResponse = mutableListOf<EnterPresentObject>()
                    for (i in stages.indices) {
                        val stageResponse = EnterStageObject(
                            stages[i].id,
                            stages[i].idPresent,
                            stages[i].idGame,
                            stages[i].textStage,
                            stages[i].hintText,
                            stages[i].long,
                            stages[i].lat,
                            stages[i].is_done
                        )
                        val presentResponse = EnterPresentObject(presents!![i].text, presents[i].image ?: "", presents[i].link)
                        stagesResponse.add(stageResponse)
                        presentsResponse.add(presentResponse)
                    }
                    val enterGame = EnterGameObject(game.id, game.idAdmin, game.idUser, game.date, game.idChat)
                    val enterResponse = EnterResponse(enterGame, stagesResponse, presentsResponse)

                    call.respond(enterResponse)
                } else {
                    call.respond(Utils.getErrorResponse(404, "Игра не найдена"))
                    return
                }
            } else {
                call.respond(Utils.getErrorResponse(404, "Игра не найдена"))
                return
            }
        } else {
            call.respond(Utils.getErrorResponse(500, "Ошибка сервера"))
            return
        }
    }

    @Serializable
    data class EnterResponse(
        val game: EnterGameObject,
        val stages: List<EnterStageObject>,
        val presents: List<EnterPresentObject>
    )
    @Serializable
    data class EnterGameObject(
        val id_game: Int,
        val id_admin: Int,
        val id_user: Int,
        val start_date: String,
        val chat_id: Int
    )
    @Serializable
    data class EnterStageObject(
        val id_stage: Int,
        val id_present: Int,
        val id_game: Int,
        val text_stage: String,
        val hint_text: String,
        val long: Double,
        val lat: Double,
        val is_done: Boolean
    )
    @Serializable
    data class EnterPresentObject(
        val present_text: String,
        val present_img: String,
        val redirect_link: String
    )
}