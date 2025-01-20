package com.github.reapermaga.library.common.repository

import java.util.concurrent.CompletableFuture

open class LocalRepository<T, ID>:Repository<T, ID>, AsyncRepository<T, ID> {

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

    override suspend fun persistAsync(entity : T) : CompletableFuture<Void> {
        persist(entity)
        return CompletableFuture.completedFuture(null)
    }

    override suspend fun saveAsync(entity : T) : CompletableFuture<Void> {
        save(entity)
        return CompletableFuture.completedFuture(null)
    }

    override suspend fun findByIdAsync(id : ID) : CompletableFuture<T?> {
        return CompletableFuture.completedFuture(findById(id))
    }

    override suspend fun findAllAsync() : CompletableFuture<Collection<T>> {
        return CompletableFuture.completedFuture(findAll())
    }

    override suspend fun deleteByIdAsync(id : ID) : CompletableFuture<Long> {
        deleteById(id)
        return CompletableFuture.completedFuture(1)
    }

    override suspend fun deleteAllAsync() : CompletableFuture<Long> {
        val size = entities.size.toLong()
        deleteAll()
        return CompletableFuture.completedFuture(size)
    }

    override suspend fun existsByIdAsync(id : ID) : CompletableFuture<Boolean> {
        return CompletableFuture.completedFuture(existsById(id))
    }

    override suspend fun countAsync() : CompletableFuture<Long> {
        return CompletableFuture.completedFuture(count())
    }


}

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class Id