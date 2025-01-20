package com.github.reapermaga.library.common.repository

import java.util.concurrent.CompletableFuture

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

interface AsyncRepository<T, ID> {

    suspend fun persistAsync(entity: T): CompletableFuture<Void>

    suspend fun saveAsync(entity: T): CompletableFuture<Void>

    suspend fun findByIdAsync(id: ID): CompletableFuture<T?>

    suspend fun findAllAsync(): CompletableFuture<Collection<T>>

    suspend fun deleteByIdAsync(id: ID): CompletableFuture<Long>

    suspend fun deleteAllAsync(): CompletableFuture<Long>

    suspend fun existsByIdAsync(id: ID): CompletableFuture<Boolean>

    suspend fun countAsync(): CompletableFuture<Long>
}