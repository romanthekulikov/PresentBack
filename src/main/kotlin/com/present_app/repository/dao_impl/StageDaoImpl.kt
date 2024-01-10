package com.present_app.repository.dao_impl

import com.present_app.repository.dao.StageDao
import com.present_app.repository.entity.Stage
import java.sql.Connection

class StageDaoImpl(private val connection: Connection) : StageDao {
    override fun getStageList(idGame: Int): List<Stage>? {
        return try {
            val result = connection.createStatement().executeQuery("SELECT * FROM public.stage WHERE id_game = $idGame")
            val stageList = mutableListOf<Stage>()
            while (result.next()) {
                val id = result.getInt(1)
                val textStage = result.getString(2)
                val hintText = result.getString(3)
                val long = result.getDouble(4)
                val lat = result.getDouble(5)
                val resultIdGame = result.getInt(6)
                val idPresent = result.getInt(7)
                val stage = Stage(
                    id = id,
                    textStage = textStage,
                    hintText = hintText,
                    long = long,
                    lat = lat,
                    idGame = resultIdGame,
                    idPresent = idPresent
                )
                stageList.add(stage)
            }
            stageList
        } catch (e: Exception) {
            null
        }
    }

    override fun create(item: Stage): Boolean {
        return try {
            connection.createStatement().executeQuery(
                "INSERT INTO public.stage (text_stage, hint_text, \"long\", lat, id_game, id_present) " +
                        "VALUES ( '${item.textStage}', '${item.hintText}', ${item.long}, ${item.lat}, ${item.idGame}, ${item.idPresent} )"
            )
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun getItem(filter: Int): Stage? {
        TODO("Not yet implemented")
    }

    override fun deleteItem(filter: Int): Boolean {
        TODO("Not yet implemented")
    }
}