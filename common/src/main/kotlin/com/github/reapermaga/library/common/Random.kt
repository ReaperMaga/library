package com.github.reapermaga.library.common


private val randomUniqueMap = mutableMapOf<String, Int>()

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

fun <T> Collection<T>.clearRandomUnique(identifier : String) {
    randomUniqueMap.remove(identifier)
}