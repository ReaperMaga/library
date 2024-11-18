package dev.reapermaga.library.common.repository

import java.lang.reflect.Field

open class LocalRepository<T, ID>:Repository<T, ID> {

    val entities = mutableMapOf<ID, T>()

    private var idClass : Field? = null

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

    private fun retrieveId(entity : T) : ID? {
        if(entity != null) {
            if(idClass == null) {
                idClass = entity::class.java.declaredFields.first {
                    it.isAccessible = true
                    it.isAnnotationPresent(LocalId::class.java)
                }
            }
            idClass?.let { return it.get(entity) as ID }

        }
        return null
    }
}

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class LocalId