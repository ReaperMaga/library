package com.github.reapermaga.library.table

import org.jetbrains.exposed.sql.Table

open class LongTable(name : String):Table(name) {
    val id = long("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)
}