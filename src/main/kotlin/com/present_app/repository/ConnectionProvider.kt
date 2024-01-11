package com.present_app.repository

import java.sql.Connection
import java.sql.DriverManager

object ConnectionProvider {
    private var connection: Connection? = null

    fun getInstance(): Connection {
        if (connection == null) {
            connection = DriverManager.getConnection(
                "jdbc:postgresql://monorail.proxy.rlwy.net:25822/railway",
                "postgres",
                "3adCFd6ceG2-gBA*2g13*4DB43dC-2eD"
            )
//            connection = DriverManager.getConnection(
//                "jdbc:postgresql://localhost:5433/present_app_db",
//                "postgres",
//                "s1210501139"
//            )
        }
        return connection!!
    }
}