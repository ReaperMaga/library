package com.github.reapermaga.library.common

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.Duration

class TimeTest {

    @Test
    fun `Convert seconds to 2 digits`() {
        assert(0.secondsTo2Digits() == "00:00")
        assert(1.secondsTo2Digits() == "00:01")
        assert(59.secondsTo2Digits() == "00:59")
        assert(60.secondsTo2Digits() == "01:00")
        assert(61.secondsTo2Digits() == "01:01")
    }

    @Test
    fun `Convert millis to time format`() {
        Assertions.assertEquals(1000L.millisToTimeFormat(), "1sec")
        Assertions.assertEquals(70000L.millisToTimeFormat(), "1m 10sec")
    }

}