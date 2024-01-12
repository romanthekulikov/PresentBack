package com.present_app.routing_cases

import com.present_app.Utils
import com.present_app.repository.dao.StageDao
import com.present_app.repository.dao_impl.StageDaoImpl
import io.ktor.server.application.*
import io.ktor.server.response.*
import java.sql.Connection

class CheckStageKeyRouting(private val call: ApplicationCall, private val connection: Connection) {
    suspend fun check() {
        val stageId = call.parameters["stage_id"]!!.toInt()
        val key = call.parameters["key"]

        val stageDao: StageDao = StageDaoImpl(connection = connection)
        val stage = stageDao.getItem(stageId)!!
        if (stage.key_present_game == key) {
            stageDao.doneStage(stage.id)
            call.respond(Utils.getErrorResponse(200, "Ключ подошел!"))
        } else {
            call.respond(Utils.getErrorResponse(400, "Неправильный код!"))
        }

    }
}