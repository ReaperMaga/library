package com.github.reapermaga.library.common.repository

import java.lang.reflect.Field

val cachedIdClasses = mutableMapOf<Class<out Repository<*, *>>, Field>()

fun <T, ID> Repository<T, ID>.retrieveId(entity : T): ID {
    if(entity != null) {
        if(!cachedIdClasses.containsKey(this::class.java)) {
            cachedIdClasses[this::class.java] = entity::class.java.declaredFields.first {
                it.isAccessible = true
                it.isAnnotationPresent(Id::class.java)
            }
        }
        val idClass = cachedIdClasses[this::class.java]
        if(idClass != null) {
            return idClass.get(entity) as ID
        }
    }
    throw NullPointerException("Entity is null or does not have an id field")
}