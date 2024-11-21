package com.github.reapermaga.library.common.repository

open class CachedRepository<T, ID>(val repository : Repository<T, ID>):Repository<T, ID> {

    val cache = mutableMapOf<ID, T>()

    override fun persist(entity : T) {
        repository.persist(entity)
        cache[retrieveId(entity)] = entity
    }

    override fun save(entity : T) {
        repository.save(entity)
        cache[retrieveId(entity)] = entity
    }

    override fun findById(id : ID) : T? {
        if (cache.containsKey(id)) {
            return cache[id]
        }
        val result = repository.findById(id)
        if (result != null) {
            cache[id] = result
        }
        return result
    }

    override fun findAll() : Collection<T> {
        return repository.findAll()
    }

    override fun deleteById(id : ID) {
        repository.deleteById(id)
        cache.remove(id)
    }

    override fun deleteAll() {
        repository.deleteAll()
        cache.clear()
    }

    override fun existsById(id : ID) : Boolean {
        return cache.containsKey(id) || repository.existsById(id)
    }

    override fun count() : Long {
        return repository.count()
    }

    fun findAllCached() : Collection<T> {
        return cache.values
    }

    data class CacheOptions(val expireAfterWrite : Long)
}