package com.github.reapermaga.library.common

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

class TimeTest {
    @Test
    fun testPadded() {
        val duration = 3661000L // 1 hour, 1 minute, and 1 second in milliseconds
        val formatted = duration.toPaddedTimeString(TimeUnit.MILLISECONDS, 3)
        Assertions.assertEquals("01:01:01", formatted)
    }

    @Test
    fun testHumanReadableDuration() {
        val duration = 3661000L // 1 hour, 1 minute, and 1 second in milliseconds
        val formatted = duration.toHumanReadableDuration(TimeUnit.MILLISECONDS)
        println(formatted)
        Assertions.assertEquals("1h 1m 1s", formatted)
    }
}
