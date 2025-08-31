package com.github.reapermaga.library.common

import kotlin.test.Test

class RandomTest {
    @Test
    fun `Test random unique`() {
        val list = listOf("a", "b")
        var previousRandom = list.randomUnique("test")
        for (i in 0..10) {
            val random = list.randomUnique("test")
            if (random == previousRandom) {
                assert(false)
            }
            previousRandom = random
        }
    }

    @Test
    fun `Test random by chance`() {
        val items = listOf(Item("a", 0.1), Item("b", 0.5))
        for (i in 0..100) {
            val random = items.randomByChance()
            println("${random.name} / ${random.chance}")
        }
    }

    class Item(
        val name: String,
        override var chance: Double,
    ) : RandomChanceItem
}
