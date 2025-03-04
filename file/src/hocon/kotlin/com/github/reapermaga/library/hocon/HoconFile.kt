package com.github.reapermaga.library.hocon

import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigRenderOptions
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.serializer
import java.io.File
import kotlin.reflect.KClass

/**
 * A wrapper for a file that contains a single entity of type [T] serialized with Hocon.
 *
 * @param path The path to the file.
 * @param type The type of the entity.
 * @property entity The entity that is stored in the file.
 * @property file The file that contains the entity.
 */
open class HoconFile<T:Any>(val path : String, val type : KClass<T>) {

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
    @OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)
    fun load() {
        val text = file.readText()
        if (text.isBlank()) return
        internalEntity = Hocon.decodeFromConfig<T>(type.serializer(), ConfigFactory.parseString(text))
    }

    /**
     * Saves the entity to the file.
     */
    @OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
    fun save() {
        if (internalEntity == null) return
        file.writeText(
            Hocon.encodeToConfig(type.serializer(), internalEntity!!).root().render(
                ConfigRenderOptions.defaults().setOriginComments(false).setJson(false)
            )
        )
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

