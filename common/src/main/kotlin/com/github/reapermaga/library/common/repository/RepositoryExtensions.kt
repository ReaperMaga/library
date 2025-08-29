package com.github.reapermaga.library.common.repository

import java.lang.reflect.Field

private val cachedIdClasses = mutableMapOf<Class<*>, Field>()

/**
 * Retrieve the id of an entity via reflection.
 *
 * @param entity Entity to retrieve the id from.
 * @throws NullPointerException If the entity is null or does not have an id field.
 * @return The id of the entity.
 */
@Suppress("UNCHECKED_CAST")
fun <T, ID> Repository<T, ID>.retrieveId(entity: T): ID {
    if (entity != null) {
        if (!cachedIdClasses.containsKey(entity::class.java)) {
            cachedIdClasses[entity::class.java] = entity::class.java.declaredFields.first {
                it.isAccessible = true
                it.isAnnotationPresent(Id::class.java)
            }
        }
        val idClass = cachedIdClasses[entity::class.java]
        if (idClass != null) {
            val id = idClass.get(entity)
            if (id != null) {
                return id as ID
            }
        }
    }
    throw NullPointerException("Entity is null or does not have an id field")
}