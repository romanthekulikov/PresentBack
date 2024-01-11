package com.present_app.routing_cases

import com.present_app.Utils
import com.present_app.plugins.sendError
import com.present_app.repository.dao.UserDao
import com.present_app.repository.dao_impl.UserDaoImpl
import com.present_app.repository.entity.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable
import java.sql.Connection

class AuthRouting(private val call: ApplicationCall, private val connection: Connection) {
    suspend fun doAuth() {
        val email = call.parameters["email"]
        val password = call.parameters["password"]
        try {
            val userDao: UserDao = UserDaoImpl(connection = connection)
            val user = userDao.getItem(email!!)
            if (user != null) {
                if (user.password == password) {
                    sendData(user = user)
                } else {
                    call.respond(Utils.getErrorResponse(404, "Не верный пароль"))
                }
            } else {
                call.respond(Utils.getErrorResponse(404, "Пользователь не найден"))
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "Ошибка сервера")
        }
    }

    @Serializable
    private data class AuthResponse(
        val user_id: Int,
        val email: String,
        val name: String,
        val icon: String
    )

    private suspend fun sendData(user: User) {
        val res = AuthResponse(user.id, user.email, user.name, user.icon)
        call.respond(res)
    }

}