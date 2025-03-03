package com.github.reapermaga.library.gson

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileReader


/**
 * A wrapper for a file that contains a single entity of type [T] serialized with Gson.
 *
 * @param path The path to the file.
 * @param type The type of the entity.
 * @property entity The entity that is stored in the file.
 * @property file The file that contains the entity.
 * @property gson The Gson instance used to serialize and deserialize the entity.
 * @property gsonBuilder The GsonBuilder used to create the Gson instance.
 */
open class GsonFile<T>(val path : String, val type : TypeToken<T>) {

    protected open val gsonBuilder : GsonBuilder = GsonBuilder()
        .setPrettyPrinting()

    private val gson by lazy(LazyThreadSafetyMode.NONE) { gsonBuilder.create() }

    val file by lazy(LazyThreadSafetyMode.NONE) {
        File(path).apply {
            if (parentFile != null && !parentFile.exists()) {
                parentFile.mkdirs()
            }
            if (!exists()) {
                createNewFile()
            }
        }
    }

    private var internalEntity : T? = null

    var entity: T
        get() = internalEntity!!
        set(value) {
            internalEntity = value
        }

    /**
     * Loads the entity from the file.
     */
    fun load() {
        val reader = FileReader(file)
        internalEntity = gson.fromJson(reader, type)
    }

    /**
     * Saves the entity to the file.
     */
    fun save() {
        file.writeText(gson.toJson(entity))
    }

    /**
     * Deletes the file.
     */
    fun delete() {
        file.delete()
    }

    /**
     * Checks if the file exists.
     */
    fun exists() = file.exists()

}

