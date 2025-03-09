package com.github.reapermaga.library.entity

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import kotlin.reflect.KProperty

inline fun <reified T:ExposedEntity> createEntity(row : ResultRow) : T {
    return createEntity(T::class.java, row)
}

fun <T:ExposedEntity> createEntity(type : Class<T>, row : ResultRow) : T {
    val instance : T = type.constructors.first().newInstance() as T
    instance.columnBindings.forEach { (name, binding) ->
        val value = row[binding.column]
        if (value == null) return@forEach
        binding.setInternalValue(value)
    }
    instance.subEntities.forEach { entity ->
        entity.setInternalValue(createEntity(entity.type, row))
    }
    return instance
}


abstract class ExposedEntity {

    val columnBindings = mutableMapOf<String, ColumnBinding<*>>()
    val subEntities = mutableListOf<SubEntityBinding<out ExposedEntity>>()

    inner class ColumnBinding<T>(val column : Column<T>, defaultValue : T? = null) {

        private var internalValue : T? = defaultValue

        init {
            columnBindings[column.name] = this
        }

        operator fun getValue(thisRef : Any?, property : KProperty<*>) : T {
            return internalValue!!
        }

        internal operator fun setValue(thisRef : Any?, property : KProperty<*>, value : T) {
            internalValue = value
        }

        fun setInternalValue(value : Any) {
            @Suppress("UNCHECKED_CAST")
            internalValue = value as T
        }
    }

    fun <T> bind(column : Column<T>) : ColumnBinding<T> = ColumnBinding(column)
    fun <T> bind(column : Column<T>, defaultValue : T) : ColumnBinding<T> = ColumnBinding(column, defaultValue)

    inline fun <reified T:ExposedEntity> bindEntity() : SubEntityBinding<T> {
        val entity = SubEntityBinding(T::class.java)
        subEntities.add(entity)
        return entity
    }

    inner class SubEntityBinding<T:ExposedEntity>(val type : Class<T>, defaultValue : T? = null) {

        private var internalValue : T? = defaultValue

        init {
            subEntities.add(this)
        }

        operator fun getValue(thisRef : Any?, property : KProperty<*>) : T {
            return internalValue!!
        }

        internal operator fun setValue(thisRef : Any?, property : KProperty<*>, value : T) {
            internalValue = value
        }

        fun setInternalValue(value : Any) {
            @Suppress("UNCHECKED_CAST")
            internalValue = value as T
        }

    }

}