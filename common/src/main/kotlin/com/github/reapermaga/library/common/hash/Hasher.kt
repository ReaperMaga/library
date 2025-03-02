package com.github.reapermaga.library.common.hash

/**
 * It's a general interface that allows to hash and verify any type of text.
 * Most common use cases are password hashing and verification.
 */
interface Hasher {

    /**
     * Hashes the given text.
     *
     * @return The hashed text.
     */
    fun hash(text: String): String

    /**
     * Verifies if the given text matches the hashed text.
     *
     * @param text The text to verify.
     * @param hashedText The hashed text to compare with.
     * @return True if the text matches the hashed text, false otherwise.
     */
    fun verify(text: String, hashedText: String): Boolean

}