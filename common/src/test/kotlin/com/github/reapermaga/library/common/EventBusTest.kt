package com.github.reapermaga.library.common

import org.junit.jupiter.api.Test

class EventBusTest {
    @Test
    fun test() {
        val bus = EventBus()

        bus.subscribe<String>(200) {
            println("This is a high priority subscription: $it")
        }
        bus.subscribe<String> {
            println(it)
        }
        bus.publish("Hello, world!")
    }
}
