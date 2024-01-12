package com.present_app.routing_cases

import com.present_app.Utils
import com.present_app.repository.dao.UserDao
import com.present_app.repository.dao_impl.UserDaoImpl
import io.ktor.server.application.*
import io.ktor.server.response.*
import java.sql.Connection

class UpdateUserRouting(private val call: ApplicationCall, private val connection: Connection) {
    suspend fun update() {
        val userId = call.parameters["user_id"]!!.toInt()
        val name = call.parameters["name"]
        val icon = call.parameters["icon"]
        val userDao: UserDao = UserDaoImpl(connection = connection)
        if (!name.isNullOrEmpty()) {
            userDao.updateName(name, userId)
        }
        if (!icon.isNullOrEmpty()) {
            userDao.updateIcon(icon, userId)
        }

        call.respond(Utils.getErrorResponse(200, "Информация сохранена"))
    }
}