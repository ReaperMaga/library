package com.github.reapermaga.library.common.repository

abstract class SuspendedLocalRepository<T : Any, ID> :
    Repository<T, ID>,
    SuspendedRepository<T, ID> {
    val entities = mutableMapOf<ID, T>()

    override fun persist(entity: T) {
        val id = retrieveId(entity)
        if (id != null) {
            entities[id] = entity
        }
    }

    override fun save(entity: T) {
        persist(entity)
    }

    override fun findById(id: ID): T? = entities[id]

    override fun findAll(): Collection<T> = entities.values

    override fun deleteById(id: ID) {
        entities.remove(id)
    }

    override fun deleteAll() {
        entities.clear()
    }

    override fun existsById(id: ID): Boolean = entities.containsKey(id)

    override fun count(): Long = entities.size.toLong()

    override suspend fun persistSuspended(entity: T) = persist(entity)

    override suspend fun saveSuspended(entity: T) = save(entity)

    override suspend fun findByIdSuspended(id: ID): T? = findById(id)

    override suspend fun findAllSuspended(): Collection<T> = findAll()

    override suspend fun existsByIdSuspended(id: ID): Boolean = existsByIdSuspended(id)

    override suspend fun deleteAllSuspended() = deleteAll()

    override suspend fun deleteByIdSuspended(id: ID) = deleteById(id)

    override suspend fun countSuspended(): Long = count()
}
