package com.github.reapermaga.library.kdi

import org.junit.jupiter.api.Test

class InterfaceTest {

    @Inject
    lateinit var testService: TestInterface

    @Test
    fun testInterface() {
        createKdiContext(this, "com.github.reapermaga.library.kdi")
        testService.call()
    }
}

interface TestInterface {
    fun call()
}

@Scoped
class TestService : TestInterface {
    override fun call() {
        println("TestService executed")
    }
}

