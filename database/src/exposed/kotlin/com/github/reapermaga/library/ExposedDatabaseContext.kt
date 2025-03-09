package com.github.reapermaga.library

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import javax.sql.DataSource

/**
 * A class that manages the database connection using HikariCP and Exposed.
 *
 * @param driver The JDBC driver class name.
 * @param url The JDBC URL for the database.
 * @param user The database user.
 * @param password The database password.
 */
class ExposedDatabaseContext(
    val driver: String,
    val url: String,
    val user: String,
    val password: String
) {

    private lateinit var database: Database
    private lateinit var dataSource: DataSource

    /**
     * Connects to the database using the provided configuration.
     * Initializes the HikariCP data source and connects it to Exposed.
     */
    fun connect() {
        val config = HikariConfig().apply {
            jdbcUrl = url
            driverClassName = driver
            username = user
            password = password
        }
        dataSource = HikariDataSource(config)
        database = Database.connect(dataSource)
    }

    /**
     * Disconnects from the database by closing the data source connection.
     */
    fun disconnect() {
        dataSource.connection.close()
    }
}