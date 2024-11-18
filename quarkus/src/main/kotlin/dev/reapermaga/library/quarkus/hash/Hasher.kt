package dev.reapermaga.library.quarkus.hash

interface Hasher {

    fun hash(text: String): String
    fun verify(text: String, hashedText: String): Boolean

}