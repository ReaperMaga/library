package com.github.reapermaga.library.common

import kotlin.test.Test

class RandomTest {

    @Test
    fun testRandomUnique() {
        val list = listOf("a", "b")
        var previousRandom = list.randomUnique("test")
        for (i in 0..10) {
            val random = list.randomUnique("test")
            if(random == previousRandom) {
                assert(false)
            }
            previousRandom = random
        }
    }
}