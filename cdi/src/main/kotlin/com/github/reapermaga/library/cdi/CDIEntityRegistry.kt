package com.github.reapermaga.library.cdi


class CDIEntityRegistry {

    internal val entities = mutableListOf<CDIEntity>()

    /**
     * Register an entity
     *
     * @param instance The instance of the entity
     */
    fun register(instance: Any) {
        this.entities.add(CDIEntity(instance))
    }

    /**
     * Register an entity class and create an instance of it,.
     *
     * @param entityClass The class of the entity
     */
    fun register(entityClass: Class<*>) {
        val constructor = entityClass.constructors.first()
        if(constructor.parameterCount >= 1) {
            val params = mutableListOf<Any>()
            for (i in 0 until constructor.parameterCount) {
                val param = constructor.parameters[i]
                val entity = getEntity(param.type)
                if (entity != null) {
                    params.add(entity.instance)
                }
            }
            register(constructor.newInstance(*params.toTypedArray()))
        } else {
            register(constructor.newInstance())
        }
    }

    /**
     * Unregister an entity
     */
    fun unregister(entity: CDIEntity) {
        entities.remove(entity)
    }

    /**
     * Unregister all entities
     */
    fun unregisterAll() {
        entities.clear()
    }

    /**
     * Get an entity by class or interface
     *
     * @param entityClass The class of the entity
     * @return The entity or null if not found
     */
    fun getEntity(entityClass: Class<*>): CDIEntity? {
        return entities.find { it.instance::class.java == entityClass || entityClass.isAssignableFrom(it.instance::class.java) }
    }

    /**
     * Get all entities by class or interface
     *
     * @param entityClass The class of the entity
     * @return The entities or an empty list if not found
     */
    fun getEntities(entityClass: Class<*>): List<CDIEntity> {
        return entities.filter { it.instance::class.java == entityClass || entityClass.isAssignableFrom(it.instance::class.java) }
    }

    fun getEntitiesByTypeAnnotation(annotation: Class<out Annotation>): List<CDIEntity> {
        return entities.filter { it.instance::class.java.isAnnotationPresent(annotation) }
    }

    fun getEntitiesInstancesByTypeAnnotation(annotation: Class<out Annotation>): List<Any> {
        return getEntitiesByTypeAnnotation(annotation).map { it.instance }
    }

}
data class CDIEntity(val instance: Any)