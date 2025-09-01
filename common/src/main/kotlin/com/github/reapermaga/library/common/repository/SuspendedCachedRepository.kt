package com.github.reapermaga.library.common.repository

import com.google.common.cache.CacheBuilder

/**
 * A cached implementation of the SuspendedRepository interface that adds caching capabilities to an existing suspended repository.
 *
 * @param T The type of the entity managed by this repository.
 * @param ID The type of the identifier for the entity.
 * @property base The underlying suspended repository to which operations are delegated.
 * @property cacheConfig A lambda to configure the cache settings using CacheBuilder.
 * @property performanceMode If true, read operations will only interact with the cache, not the base repository.
 */
abstract class SuspendedCachedRepository<T : Any, ID : Any>(
    private val base: SuspendedRepository<T, ID>,
    private val cacheConfig: CacheBuilder<Any, Any>.() -> Unit = {},
    private val performanceMode: Boolean = false,
) : SuspendedRepository<T, ID> {
    private val cache =
        CacheBuilder
            .newBuilder()
            .apply {
                cacheConfig(this)
            }.build<ID, T>()

    protected abstract val idSelector: (T) -> ID

    override suspend fun persistSuspended(entity: T) {
        base.persistSuspended(entity)
        cache.put(idSelector(entity), entity)
    }

    override suspend fun findByIdSuspended(id: ID): T? {
        if (performanceMode) return cache.getIfPresent(id)
        return cache.getIfPresent(id) ?: base.findByIdSuspended(id)?.also { cache.put(id, it) }
    }

    override suspend fun findAllSuspended(): Collection<T> {
        if (performanceMode) return cache.asMap().values
        return base.findAllSuspended()
    }

    override suspend fun deleteAllSuspended() {
        base.deleteAllSuspended()
        cache.invalidateAll()
    }

    override suspend fun deleteByIdSuspended(id: ID) {
        base.deleteByIdSuspended(id)
        cache.invalidate(id)
    }

    override suspend fun existsByIdSuspended(id: ID): Boolean {
        if (performanceMode) return cache.asMap().containsKey(id)
        return cache.asMap().containsKey(id) || base.existsByIdSuspended(id)
    }

    override suspend fun countSuspended(): Long = base.countSuspended()

    override suspend fun saveSuspended(entity: T) {
        base.saveSuspended(entity)
        cache.put(idSelector(entity), entity)
    }
}
