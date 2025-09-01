package com.github.reapermaga.library.common.repository

/**
 * A coroutine-based repository interface offering basic CRUD operations in a suspended context.
 *
 * @param T The type of the entity managed by this repository.
 * @param ID The type of the identifier for the entity.
 */
interface SuspendedRepository<T, ID> {
    suspend fun persistSuspended(entity: T)

    suspend fun saveSuspended(entity: T)

    suspend fun findByIdSuspended(id: ID): T?

    suspend fun findAllSuspended(): Collection<T>

    suspend fun deleteByIdSuspended(id: ID)

    suspend fun deleteAllSuspended()

    suspend fun existsByIdSuspended(id: ID): Boolean

    suspend fun countSuspended(): Long
}
