package com.present_app.routing_cases

import com.present_app.repository.dao.UserDao
import com.present_app.repository.dao_impl.UserDaoImpl
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable
import java.sql.Connection

class RespondUserInfo(private val call: ApplicationCall, private val connection: Connection) {
    suspend fun respond() {
        val email = call.parameters["email"]!!

        val userDao: UserDao = UserDaoImpl(connection = connection)
        val user = userDao.getItem(email)!!
        val response = UserResponse(email = user.email, id = user.id, name = user.name, icon = user.icon)

        call.respond(response)
    }

    @Serializable
    data class UserResponse(
        val email: String,
        val id: Int,
        val name: String,
        val icon: String
    )
}