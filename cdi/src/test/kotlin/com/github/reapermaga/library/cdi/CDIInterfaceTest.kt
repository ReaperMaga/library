package com.github.reapermaga.library.cdi

import org.junit.jupiter.api.Test

class CDIInterfaceTest {

    @Inject
    lateinit var testService : TestInterface

    @Test
    fun testBasic() {
        val cdi = CDI()
        cdi.run(this, "com.github.reapermaga.library.cdi")

        testService.call()
    }
}

interface TestInterface {
    fun call()
}

@Scoped
class TestService:TestInterface {
    override fun call() {
        println("TestService executed")
    }
}

