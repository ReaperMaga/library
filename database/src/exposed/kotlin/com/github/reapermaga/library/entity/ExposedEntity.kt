package com.github.reapermaga.library.entity

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import kotlin.reflect.KProperty

/**
 * Creates an instance of the specified `ExposedEntity` type from a `ResultRow`.
 * This helper function is used for mapping the data from a `ResultRow` to an entity.
 *
 * @param row The `ResultRow` containing the data.
 * @return An instance of the specified `ExposedEntity` type.
 */
inline fun <reified T: ExposedEntity> createEntity(row: ResultRow): T {
    return createEntity(T::class.java, row)
}

/**
 * Creates an instance of the specified `ExposedEntity` type from a `ResultRow`.
 * This helper function is used for mapping the data from a `ResultRow` to an entity.
 *
 * @param type The class type of the `ExposedEntity`.
 * @param row The `ResultRow` containing the data.
 * @return An instance of the specified `ExposedEntity` type.
 */
@Suppress("UNCHECKED_CAST")
fun <T: ExposedEntity> createEntity(type: Class<T>, row: ResultRow): T {
    val instance: T = type.constructors.first().newInstance() as T
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

/**
 * Abstract class representing an entity in the Exposed framework.
 */
abstract class ExposedEntity {

    /**
     * A map of column bindings for the entity.
     */
    @Transient
    val columnBindings = mutableMapOf<String, ColumnBinding<*>>()

    /**
     * A list of sub-entity bindings for the entity.
     */
    @Transient
    val subEntities = mutableListOf<SubEntityBinding<out ExposedEntity>>()

    /**
     * Class representing a binding between a column and its value.
     *
     * @param T The type of the column.
     * @param column The column to bind.
     * @param defaultValue The default value of the column.
     */
    inner class ColumnBinding<T>(val column: Column<T>, defaultValue: T? = null) {

        @Transient
        private var internalValue: T? = defaultValue

        init {
            columnBindings[column.name] = this
        }

        operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return internalValue!!
        }

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            internalValue = value
        }

        fun setInternalValue(value: Any) {
            @Suppress("UNCHECKED_CAST")
            internalValue = value as T
        }
    }

    /**
     * Binds a column to the entity.
     *
     * @param column The column to bind.
     * @return The column binding.
     */
    fun <T> bind(column: Column<T>): ColumnBinding<T> = ColumnBinding(column)

    /**
     * Binds a column to the entity with a default value.
     *
     * @param column The column to bind.
     * @param defaultValue The default value of the column.
     * @return The column binding.
     */
    fun <T> bind(column: Column<T>, defaultValue: T): ColumnBinding<T> = ColumnBinding(column, defaultValue)

    /**
     * Binds a sub-entity to the entity.
     *
     * @return The sub-entity binding.
     */
    inline fun <reified T: ExposedEntity> bindEntity(): SubEntityBinding<T> {
        val entity = SubEntityBinding(T::class.java)
        subEntities.add(entity)
        return entity
    }

    /**
     * Class representing a binding between a sub-entity and its value.
     *
     * @param T The type of the sub-entity.
     * @param type The class type of the sub-entity.
     * @param defaultValue The default value of the sub-entity.
     */
    inner class SubEntityBinding<T: ExposedEntity>(val type: Class<T>, defaultValue: T? = null) {

        @Transient
        private var internalValue: T? = defaultValue

        init {
            subEntities.add(this)
        }

        operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return internalValue!!
        }

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            internalValue = value
        }

        fun setInternalValue(value: Any) {
            @Suppress("UNCHECKED_CAST")
            internalValue = value as T
        }
    }
}