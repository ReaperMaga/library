package com.github.reapermaga.library.common.repository

open class LocalRepository<T, ID>:Repository<T, ID> {

    val entities = mutableMapOf<ID, T>()

    override fun persist(entity : T) {
        val id = retrieveId(entity)
        if (id != null) {
            entities[id] = entity
        }
    }

    override fun save(entity : T) {
        persist(entity)
    }

    override fun findById(id : ID) : T? {
        return entities[id]
    }

    override fun findAll() : Collection<T> {
        return entities.values
    }

    override fun deleteById(id : ID) {
        entities.remove(id)
    }

    override fun deleteAll() {
        entities.clear()
    }

    override fun existsById(id : ID) : Boolean {
        return entities.containsKey(id)
    }

    override fun count() : Long {
        return entities.size.toLong()
    }


}

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class Id