package com.github.reapermaga.library.common.repository

import java.util.concurrent.CompletableFuture

/**
 * Basic repository pattern interface.
 *
 * @param T Entity type.
 * @param ID Entity ID type.
 */
interface Repository<T, ID> {

    fun persist(entity: T)

    fun save(entity: T)

    fun findById(id: ID): T?

    fun findAll(): Collection<T>

    fun deleteById(id: ID)

    fun deleteAll()

    fun existsById(id: ID): Boolean

    fun count(): Long
}

/**
 * Asynchronous repository pattern interface.
 *
 * @param T Entity type.
 * @param ID Entity ID type.
 */
interface AsyncRepository<T, ID> {

    fun persistAsync(entity: T): CompletableFuture<Void>

    fun saveAsync(entity: T): CompletableFuture<Void>

    fun findByIdAsync(id: ID): CompletableFuture<T?>

    fun findAllAsync(): CompletableFuture<Collection<T>>

    fun deleteByIdAsync(id: ID): CompletableFuture<Long>

    fun deleteAllAsync(): CompletableFuture<Long>

    fun existsByIdAsync(id: ID): CompletableFuture<Boolean>

    fun countAsync(): CompletableFuture<Long>
}

/**
 * A coroutine-based repository interface offering basic CRUD operations in a suspended context.
 *
 * @param T The type of the entity managed by this repository.
 * @param ID The type of the identifier for the entity.
 */
interface SuspendedRepository<T, ID> {

    suspend fun persist(entity: T)

    suspend fun save(entity: T)

    suspend fun findById(id: ID): T?

    suspend fun findAll(): Collection<T>

    suspend fun deleteById(id: ID)

    suspend fun deleteAll()

    suspend fun existsById(id: ID): Boolean

    suspend fun count(): Long
}