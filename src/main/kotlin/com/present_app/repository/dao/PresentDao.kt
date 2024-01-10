package com.present_app.repository.dao

import com.present_app.repository.entity.Present
import com.present_app.repository.entity.Stage

interface PresentDao : BaseDao<Present, Int> {
    fun getLastItemBySenderId(id: Int): Present?
    fun getPresentsListByStages(stages: List<Stage>): List<Present>?
}