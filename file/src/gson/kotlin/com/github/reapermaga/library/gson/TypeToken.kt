@file:Suppress("UNCHECKED_CAST")

package com.github.reapermaga.library.gson

import com.google.gson.reflect.TypeToken
import kotlin.reflect.KClass

/**
 * Creates a [TypeToken] for a [MutableList] with the given type.
 * @param type The type of the list.
 * @return The [TypeToken] for the list.
 */
fun <T : Any> typeTokenOfMutableList(type: KClass<T>): TypeToken<MutableList<T>> = TypeToken.getParameterized(MutableList::class.java, type.java) as TypeToken<MutableList<T>>

/**
 * Creates a [TypeToken] for a [MutableMap] with the given key and value types.
 * @param key The type of the map keys.
 * @param value The type of the map values.
 * @return The [TypeToken] for the map.
 */
fun <K : Any, V : Any> typeTokenOfMutableMap(
    key: KClass<K>,
    value: KClass<V>,
): TypeToken<MutableMap<K, V>> = TypeToken.getParameterized(MutableMap::class.java, key.java, value.java) as TypeToken<MutableMap<K, V>>

/**
 * Creates a [TypeToken] for the given type [T].
 * @return The [TypeToken] for the type [T].
 */
inline fun <reified T> typeTokenOf(): TypeToken<T> = TypeToken.get(T::class.java)
