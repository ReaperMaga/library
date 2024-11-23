package com.github.reapermaga.library.cdi

import java.lang.reflect.Parameter

class CDIEntityRegistry {

    internal val entities = mutableListOf<CDIEntity>()

    fun register(instance: Any) {
        this.entities.add(CDIEntity(instance))
    }

    fun register(entityClass: Class<*>) {
        val constructor = entityClass.constructors.first()
        if(constructor.parameterCount >= 1) {
            val params = mutableListOf<Parameter>()
            for (i in 0 until constructor.parameterCount) {
                val param = constructor.parameters[i]
                val entity = getEntity(param.type)
                if (entity != null) {
                    params.add(param)
                }
            }
            register(constructor.newInstance(params))
        } else {
            register(constructor.newInstance())
        }
    }

    fun unregister(entity: CDIEntity) {
        entities.remove(entity)
    }

    fun unregisterAll() {
        entities.clear()
    }

    fun getEntity(entityClass: Class<*>): CDIEntity? {
        return entities.find { it.instance::class.java == entityClass || entityClass.isAssignableFrom(it.instance::class.java) }
    }
}
data class CDIEntity(val instance: Any)