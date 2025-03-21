package com.github.reapermaga.library.common.repository

abstract class SuspendedLocalRepository<T:Any, ID> : Repository<T, ID>, SuspendedRepository<T, ID> {

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

    override suspend fun findByIdSuspended(id: ID): T? {
        return findById(id)
    }

    override suspend fun findAllSuspended(): Collection<T> {
        return findAll()
    }

    override suspend fun existsByIdSuspended(id: ID): Boolean {
        return existsByIdSuspended(id)
    }

    override suspend fun deleteAllSuspended() {
        return deleteAll()
    }

    override suspend fun deleteByIdSuspended(id: ID) {
        return deleteById(id)
    }

    override suspend fun countSuspended(): Long {
        return count()
    }
}