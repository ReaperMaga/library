package com.github.reapermaga.library

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import javax.sql.DataSource

class ExposedDatabaseContext(
    val driver : String,
    val url : String,
    val user : String,
    val password : String
) {

    private lateinit var database : Database
    private lateinit var dataSource : DataSource

    fun connect() {
        val config = HikariConfig()
        config.jdbcUrl = url
        config.driverClassName = driver
        config.username = user
        config.password = password
        dataSource = HikariDataSource(config)

        database = Database.connect(dataSource)
    }

    fun disconnect() {
        dataSource.connection.close()
    }


}