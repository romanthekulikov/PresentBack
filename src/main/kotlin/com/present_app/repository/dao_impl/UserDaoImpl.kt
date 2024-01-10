package com.present_app.repository.dao_impl

import com.present_app.repository.dao.UserDao
import com.present_app.repository.entity.User
import java.sql.Connection

class UserDaoImpl(private val connection: Connection) : UserDao {
    override fun create(item: User): Boolean {
        return try {
            connection.createStatement().executeQuery(
                "INSERT INTO public.\"user\" (\"password\", email, \"name\", icon) VALUES ( '${item.password}', '${item.email}', '${item.name}', '${item.icon}' )"
            )

            true
        } catch (e: Exception) {
            false
        }
    }

    override fun getItem(filter: String): User? {
        return try {
            val result = connection.createStatement().executeQuery(
                "SELECT email, \"password\", \"name\", icon FROM public.\"user\" WHERE email = '$filter'"
            )
            if (result.next()) {
                val password = result.getString(2)
                val email = result.getString(1)
                val name = result.getString(3)
                val icon = result.getString(4)

                User(password = password, email = email, icon = icon, name = name)
            } else {
                null
            }

        } catch (e: Exception) {
            null
        }

    }

    override fun deleteItem(filter: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateItem(): Boolean {
        TODO("Not yet implemented")
    }

}