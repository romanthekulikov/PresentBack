package com.present_app.routing_cases

import com.present_app.repository.dao_impl.UserDaoImpl
import io.ktor.server.application.*
import io.ktor.server.response.*
import java.sql.Connection

class RespondUserRouting(private val call: ApplicationCall, private val connection: Connection) {
    suspend fun respond() {
        val userId = call.parameters["user_id"]!!.toInt()
        val userDao = UserDaoImpl(connection)
        try {
            val user = userDao.getUserById(userId)!!
            call.respond(userDao.getUserById(userId)!!)
        } catch (e: Exception) {
            println(e.message)
        }

    }
}