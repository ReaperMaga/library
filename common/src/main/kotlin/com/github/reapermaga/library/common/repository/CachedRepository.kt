package com.github.reapermaga.library.common.repository

import com.google.common.cache.CacheBuilder

/**
 * A cached implementation of the Repository interface that adds caching capabilities to an existing repository.
 *
 * @param T The type of the entity managed by this repository.
 * @param ID The type of the identifier for the entity.
 * @property base The underlying repository to which operations are delegated.
 * @property cacheConfig A lambda to configure the cache settings using CacheBuilder.
 * @property performanceMode If true, read operations will only interact with the cache, not the base repository.
 */
abstract class CachedRepository<T : Any, ID : Any>(
    private val base: Repository<T, ID>,
    private val cacheConfig: CacheBuilder<Any, Any>.() -> Unit = {},
    private val performanceMode: Boolean = false,
) : Repository<T, ID> {
    private val cache =
        CacheBuilder
            .newBuilder()
            .apply {
                cacheConfig(this)
            }.build<ID, T>()

    protected abstract val idSelector: (T) -> ID

    override fun persist(entity: T) {
        base.persist(entity)
        cache.put(idSelector(entity), entity)
    }

    override fun findById(id: ID): T? {
        if (performanceMode) return cache.getIfPresent(id)
        return cache.getIfPresent(id) ?: base.findById(id)?.also { cache.put(id, it) }
    }

    override fun findAll(): Collection<T> {
        if (performanceMode) return cache.asMap().values
        return base.findAll()
    }

    override fun deleteAll() {
        base.deleteAll()
        cache.invalidateAll()
    }

    override fun deleteById(id: ID) {
        base.deleteById(id)
        cache.invalidate(id)
    }

    override fun existsById(id: ID): Boolean {
        if (performanceMode) return cache.asMap().containsKey(id)
        return cache.asMap().containsKey(id) || base.existsById(id)
    }

    override fun count(): Long = base.count()

    override fun save(entity: T) {
        base.save(entity)
        cache.put(idSelector(entity), entity)
    }
}
