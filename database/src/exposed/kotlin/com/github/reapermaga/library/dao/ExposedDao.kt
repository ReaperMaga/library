package com.github.reapermaga.library.dao

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * A Data Access Object (DAO) class for managing database operations using Exposed.
 *
 * @param ID The type of the primary key.
 * @param table The main table associated with this DAO.
 * @param joinTables Additional tables to join with the main table.
 */
open class ExposedDao<ID: Any>(val table: Table, vararg val joinTables: Table) {

    init {
        transaction {
            SchemaUtils.create(table)
            joinTables.forEach {
                SchemaUtils.create(it)
            }
        }
    }

    /**
     * Lazy property to get the single primary key column of the table.
     * Throws an error if the table has no primary key or a composite primary key.
     */
    val singlePrimaryKey by lazy {
        val rawPrimaryKey = table.primaryKey ?: error("No primary key")
        val column = rawPrimaryKey.columns.singleOrNull() ?: error("Composite primary key")
        @Suppress("UNCHECKED_CAST")
        column as Column<ID>
    }

    /**
     * Joins the main table with other tables using the specified join type.
     *
     * @param type The type of join (INNER, LEFT, etc.).
     * @param otherTables The tables to join with the main table.
     * @return The last joined table.
     */
    protected suspend fun join(type: JoinType, vararg otherTables: Table) = runQuery {
        otherTables.map { table.join(it, type) }.last()
    }

    /**
     * Joins the main table with the join tables specified in the constructor using the specified join type.
     *
     * @param type The type of join (INNER, LEFT, etc.).
     * @return The last joined table.
     */
    protected suspend fun joins(type: JoinType) = join(type, *joinTables)

    /**
     * Joins the main table with the join tables and selects all rows.
     *
     * @param type The type of join (INNER, LEFT, etc.).
     * @return The result of the select all query.
     */
    protected suspend fun joinsAndSelectAll(type: JoinType) = joins(type).selectAll()

    /**
     * Finds a row by its primary key.
     *
     * @param id The primary key value.
     * @param map A function to map the result row to a desired type.
     * @return The mapped result or null if no row is found.
     */
    protected suspend fun <T> findById(id: ID, map: (row: ResultRow) -> T) = runQuery {
        table.selectAll().where { singlePrimaryKey eq id }.firstOrNull()?.let { map(it) }
    }

    /**
     * Finds all rows in the table.
     *
     * @param map A function to map each result row to a desired type.
     * @return A list of mapped results.
     */
    protected suspend fun <T> findAll(map: (row: ResultRow) -> T) = runQuery {
        table.selectAll().map { map(it) }
    }

    /**
     * Finds all rows in the table with pagination.
     *
     * @param limit The maximum number of rows to return.
     * @param offset The offset from the start of the result set.
     * @param map A function to map each result row to a desired type.
     * @return A list of mapped results.
     */
    protected suspend fun <T> findAll(limit: Int, offset: Long, map: (row: ResultRow) -> T) = runQuery {
        table.selectAll().limit(limit).offset(offset).map { map(it) }
    }

    /**
     * Deletes a row by its primary key.
     *
     * @param id The primary key value.
     * @return The number of rows deleted.
     */
    suspend fun deleteById(id: ID) = runQuery {
        table.deleteWhere { singlePrimaryKey eq id }
    }

    /**
     * Deletes all rows in the table.
     *
     * @return The number of rows deleted.
     */
    suspend fun deleteAll() = runQuery {
        table.deleteAll()
    }

    /**
     * Runs a database query within a suspended transaction.
     *
     * @param block The query to run.
     * @return The result of the query.
     */
    protected suspend fun <T> runQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}