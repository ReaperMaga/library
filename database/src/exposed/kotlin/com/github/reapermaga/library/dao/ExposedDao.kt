package com.github.reapermaga.library.dao

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

open class ExposedDao<ID:Any>(val table : Table, vararg val joinTables : Table) {

    init {
        transaction {
            SchemaUtils.create(table)
            joinTables.forEach {
                SchemaUtils.create(it)
            }
        }
    }

    val singlePrimaryKey by lazy {
        val rawPrimaryKey = table.primaryKey ?: error("No primary key")
        val column = rawPrimaryKey.columns.singleOrNull() ?: error("Composite primary key")
        @Suppress("UNCHECKED_CAST")
        column as Column<ID>
    }

    protected suspend fun join(type : JoinType, vararg otherTables : Table) = runQuery {
        otherTables.map { table.join(it, type) }.last()
    }

    protected suspend fun joins(type : JoinType) = join(type, *joinTables)

    protected suspend fun joinsAndSelectAll(type : JoinType) = joins(type).selectAll()

    protected suspend fun <T> findById(id : ID, map : (row : ResultRow) -> T) = runQuery {
        table.selectAll().where { singlePrimaryKey eq id }.map { map(it) }.singleOrNull()
    }

    protected suspend fun <T> findAll(map : (row : ResultRow) -> T) = runQuery {
        table.selectAll().map { map(it) }
    }

    protected suspend fun <T> findAll(limit : Int, offset : Long, map : (row : ResultRow) -> T) = runQuery {
        table.selectAll().limit(limit).offset(offset).map { map(it) }
    }

    suspend fun deleteById(id : ID) = runQuery {
        table.deleteWhere { singlePrimaryKey eq id }
    }

    suspend fun deleteAll() = runQuery {
        table.deleteAll()
    }

    protected suspend fun <T> runQuery(block : suspend () -> T) : T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

}