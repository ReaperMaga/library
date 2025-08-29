package com.github.reapermaga.library.common.repository

import java.util.concurrent.CompletableFuture

/**
 * Basic repository pattern interface. It uses a map to store entities.
 * Its main purpose is to provide a simple in-memory repository.
 *
 * @param T Entity type.
 * @param ID Entity ID type.
 */
open class LocalRepository<T, ID> : Repository<T, ID>, AsyncRepository<T, ID> {

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

    override fun findById(id: ID): T? {
        return entities[id]
    }

    override fun findAll(): Collection<T> {
        return entities.values
    }

    override fun deleteById(id: ID) {
        entities.remove(id)
    }

    override fun deleteAll() {
        entities.clear()
    }

    override fun existsById(id: ID): Boolean {
        return entities.containsKey(id)
    }

    override fun count(): Long {
        return entities.size.toLong()
    }

    override fun persistAsync(entity: T): CompletableFuture<Void> {
        persist(entity)
        return CompletableFuture.completedFuture(null)
    }

    override fun saveAsync(entity: T): CompletableFuture<Void> {
        save(entity)
        return CompletableFuture.completedFuture(null)
    }

    override fun findByIdAsync(id: ID): CompletableFuture<T?> {
        return CompletableFuture.completedFuture(findById(id))
    }

    override fun findAllAsync(): CompletableFuture<Collection<T>> {
        return CompletableFuture.completedFuture(findAll())
    }

    override fun deleteByIdAsync(id: ID): CompletableFuture<Long> {
        deleteById(id)
        return CompletableFuture.completedFuture(1)
    }

    override fun deleteAllAsync(): CompletableFuture<Long> {
        val size = entities.size.toLong()
        deleteAll()
        return CompletableFuture.completedFuture(size)
    }

    override fun existsByIdAsync(id: ID): CompletableFuture<Boolean> {
        return CompletableFuture.completedFuture(existsById(id))
    }

    override fun countAsync(): CompletableFuture<Long> {
        return CompletableFuture.completedFuture(count())
    }


}

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class Id