package com.present_app.routing_cases

import com.present_app.repository.dao.UserDao
import com.present_app.repository.dao_impl.UserDaoImpl
import com.present_app.repository.entity.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable
import java.sql.Connection

class RegRouting(private val call: ApplicationCall, private val connection: Connection) {
    suspend fun doReg(): Boolean {
        try {

            val email = call.parameters["email"]
            val password = call.parameters["password"]
            val userDao: UserDao = UserDaoImpl(connection = connection)
            if (email != null && password != null) {
                val hasUserWithEmail = userDao.getItem(email) != null
                if (hasUserWithEmail) {
                    call.respond(HttpStatusCode.BadRequest, "Пользователь уже создан")
                    return false
                }
                val user = User(password = password, email = email, "", "")
                userDao.create(user)
                call.respond("Пользователь удачно создан")
                return true
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Ошибка сервера")
                return false
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "Ошибка сервера")
            return false
        }
    }
}