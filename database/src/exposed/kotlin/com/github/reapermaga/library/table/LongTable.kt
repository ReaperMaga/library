package com.github.reapermaga.library.table

import org.jetbrains.exposed.sql.Table

/**
 * Represents a table with a primary key of type Long.
 *
 * @param name The name of the table.
 */
open class LongTable(name: String) : Table(name) {
    /**
     * The ID column, which auto-increments.
     */
    val id = long("id").autoIncrement()

    /**
     * The primary key for the table, which is the ID column.
     */
    override val primaryKey = PrimaryKey(id)
}