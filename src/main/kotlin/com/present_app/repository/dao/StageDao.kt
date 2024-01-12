package com.present_app.repository.dao

import com.present_app.repository.entity.Stage

interface StageDao : BaseDao<Stage, Int> {
    fun getStageList(idGame: Int): List<Stage>?
    fun doneStage(idStage: Int)
}