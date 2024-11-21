package com.github.reapermaga.library.common.repository

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

    suspend fun persist(entity: T)

    suspend fun save(entity: T)

    suspend fun findById(id: ID): T?

    suspend fun findAll(): Collection<T>

    suspend fun deleteById(id: ID)

    suspend fun deleteAll()

    suspend fun existsById(id: ID): Boolean

    suspend fun count(): Long
}