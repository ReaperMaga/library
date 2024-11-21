package com.github.reapermaga.library.common.hash

interface Hasher {

    fun hash(text: String): String
    fun verify(text: String, hashedText: String): Boolean

}