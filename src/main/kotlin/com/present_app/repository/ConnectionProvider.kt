package com.present_app.repository

import java.sql.Connection
import java.sql.DriverManager

object ConnectionProvider {
    private var connection: Connection? = null

    fun getInstance(): Connection {
        if (connection == null) {
            connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5433/present_app_db",
                "postgres",
                "s1210501139"
            )
        }
        return connection!!
    }
}