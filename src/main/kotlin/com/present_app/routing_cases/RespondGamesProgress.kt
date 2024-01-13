package com.present_app.routing_cases

import com.present_app.repository.dao.GameDao
import com.present_app.repository.dao.StageDao
import com.present_app.repository.dao_impl.GameDaoImpl
import com.present_app.repository.dao_impl.StageDaoImpl
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable
import java.sql.Connection

class RespondGamesProgress(private val call: ApplicationCall, private val connection: Connection) {
    suspend fun respond() {
        val idUser = call.parameters["user_id"]!!.toInt()
        val gameDao: GameDao = GameDaoImpl(connection = connection)
        val stageDao: StageDao = StageDaoImpl(connection = connection)
        val progresses = mutableListOf<GamesProgressResponse>()
        val games = gameDao.getAllGameByUserId(idUser)
        if (games != null) {
            for (game in games) {
                val stages = stageDao.getStageList(game.id)
                var doneStagesCount = 0
                if (stages != null) {
                    for (stage in stages) {
                        if (stage.is_done) {
                            doneStagesCount += 1
                        }
                    }
                    var done = 0.0
                    if (doneStagesCount > 0) {
                        done = doneStagesCount / stages.count().toDouble()
                    }
                    progresses.add(
                        GamesProgressResponse(
                            id_admin = game.idAdmin,
                            done * 100,
                            "$doneStagesCount/${stages.count()}"
                        )
                    )
                }
            }
            call.respond(progresses)
        }
    }
    @Serializable
    data class GamesProgressResponse(
        val id_admin: Int,
        val progress: Double,
        val progress_string: String
    )
}