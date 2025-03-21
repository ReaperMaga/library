package com.github.reapermaga.library.common

import java.util.concurrent.ThreadLocalRandom


private val randomUniqueMap = mutableMapOf<String, Int>()

/**
 * Returns a random element from the collection. It will not return the same element twice in a row.
 *
 * @param identifier A unique identifier for the random element. If the same identifier is used, the same element will not be returned.
 * @throws IllegalArgumentException if the collection is empty.
 * @return A random element from the collection.
 */
tailrec fun <T> Collection<T>.randomUnique(identifier : String) : T {
    if (this.isEmpty()) throw IllegalArgumentException("Collection is empty")
    if (size == 1) return first()
    val randomObj = random()
    if (randomUniqueMap.containsKey(identifier)) {
        if (randomUniqueMap[identifier] == randomObj.hashCode()) {
            return randomUnique(identifier)
        }
        randomUniqueMap[identifier] = randomObj.hashCode()
        return randomObj
    }
    randomUniqueMap[identifier] = randomObj.hashCode()
    return randomObj
}

/**
 * Clears the random element for the given identifier. This need to be called to avoid memory leaks.
 *
 * @param identifier The identifier to clear the random element for.
 */
fun <T> Collection<T>.clearRandomUnique(identifier : String) {
    randomUniqueMap.remove(identifier)
}

interface RandomChanceItem {
    var chance: Double
}

/**
 * Returns a random element from the collection based on the chance of each element.
 *
 * @throws IllegalArgumentException if the collection is empty.
 * @return A random element from the collection.
 */
fun <T : RandomChanceItem> Collection<T>.randomByChance(): T {
    if(this.isEmpty()) throw IllegalArgumentException("Collection is empty")
    if(this.size == 1) return first()
    val totalChance = sumOf { it.chance }
    val randomValue = ThreadLocalRandom.current().nextDouble(totalChance)
    var currentChance = 0.0
    for (item in this) {
        currentChance += item.chance
        if (randomValue <= currentChance) {
            return item
        }
    }
    return first()
}

/**
 * Returns a random integer between the specified minimum (inclusive) and maximum (inclusive) values.
 *
 * @param min The minimum value (inclusive).
 * @param max The maximum value (inclusive).
 * @return A random integer between min and max (inclusive).
 */
fun randomInt(min: Int, max: Int): Int {
    return ThreadLocalRandom.current().nextInt(min, max + 1)
}

/**
 * Returns a random long between the specified minimum (inclusive) and maximum (inclusive) values.
 *
 * @param min The minimum value (inclusive).
 * @param max The maximum value (inclusive).
 * @return A random long between min and max (inclusive).
 */
fun randomLong(min: Long, max: Long): Long {
    return ThreadLocalRandom.current().nextLong(min, max + 1)
}

/**
 * Returns a random double between the specified minimum (inclusive) and maximum (exclusive) values.
 *
 * @param min The minimum value (inclusive).
 * @param max The maximum value (exclusive).
 * @return A random double between min and max (exclusive).
 */
fun randomDouble(min: Double, max: Double): Double {
    return ThreadLocalRandom.current().nextDouble(min, max)
}

/**
 * Returns a random float between the specified minimum (inclusive) and maximum (exclusive) values.
 *
 * @param min The minimum value (inclusive).
 * @param max The maximum value (exclusive).
 * @return A random float between min and max (exclusive).
 */
fun randomFloat(min: Float, max: Float): Float {
    return ThreadLocalRandom.current().nextFloat(min, max)
}

/**
 * Returns a random boolean value.
 *
 * @return A random boolean value.
 */
fun randomBoolean(): Boolean {
    return ThreadLocalRandom.current().nextBoolean()
}