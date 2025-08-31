package com.github.reapermaga.library.gson

import com.github.reapermaga.library.common.repository.AsyncRepository
import com.github.reapermaga.library.common.repository.Repository
import com.github.reapermaga.library.common.repository.retrieveId
import com.google.gson.reflect.TypeToken
import java.io.File
import java.util.concurrent.CompletableFuture
import kotlin.reflect.KClass

/**
 * A repository that stores entities of type [T] serialized with Gson in files.
 *
 * @param path The path to the directory where the files are stored.
 * @param type The type of the entities.
 * @property directory The directory where the files are stored.
 */
open class GsonRepository<T : Any, ID>(val path: String, val type: KClass<T>) : Repository<T, ID>,
    AsyncRepository<T, ID> {

    val directory = File(path).apply { if (!exists()) mkdirs() }

    override fun persist(entity: T) {
        save(entity)
    }

    override fun save(entity: T) {
        val id = retrieveId(entity)
        val wrapper = getWrapper(id)
        wrapper.entity = entity
        wrapper.save()
    }

    override fun findById(id: ID): T? {
        val wrapper = getWrapper(id)
        wrapper.load()
        return if (wrapper.exists) wrapper.entity else null
    }

    override fun findAll(): Collection<T> {
        val list = mutableListOf<T>()
        directory.listFiles()?.forEach {
            val wrapper = GsonFile<T>(it.path, TypeToken.get(type.java))
            wrapper.load()
            wrapper.entity.let { list.add(it) }
        }
        return list
    }

    override fun deleteById(id: ID) {
        val wrapper = getWrapper(id)
        wrapper.delete()
    }

    override fun deleteAll() {
        directory.listFiles()?.forEach { it.delete() }
    }

    override fun existsById(id: ID): Boolean {
        return getWrapper(id).file.exists()
    }

    override fun count(): Long {
        return directory.listFiles()?.size?.toLong() ?: 0
    }

    private fun getWrapper(id: ID): GsonFile<T> {
        return GsonFile("$path${id.toString()}.json", TypeToken.get(type.java))
    }

    override fun persistAsync(entity: T): CompletableFuture<Void> {
        return saveAsync(entity)
    }

    override fun saveAsync(entity: T): CompletableFuture<Void> {
        return CompletableFuture.runAsync { save(entity) }
    }

    override fun findByIdAsync(id: ID): CompletableFuture<T?> {
        return CompletableFuture.supplyAsync { findById(id) }
    }

    override fun findAllAsync(): CompletableFuture<Collection<T>> {
        return CompletableFuture.supplyAsync { findAll() }
    }

    override fun deleteByIdAsync(id: ID): CompletableFuture<Long> {
        return CompletableFuture.supplyAsync {
            deleteById(id)
            1
        }
    }

    override fun deleteAllAsync(): CompletableFuture<Long> {
        return CompletableFuture.supplyAsync {
            deleteAll()
            count()
        }
    }

    override fun existsByIdAsync(id: ID): CompletableFuture<Boolean> {
        return CompletableFuture.supplyAsync { existsById(id) }
    }

    override fun countAsync(): CompletableFuture<Long> {
        return CompletableFuture.supplyAsync { count() }
    }
}