package com.github.reapermaga.library.cdi

import org.junit.jupiter.api.Test

class CDIBasicTest {

    @Inject
    lateinit var testBasicService: TestBasicService

    @Test
    fun testBasic() {
        val cdi = CDI()
        cdi.run(this, "com.github.reapermaga.library.cdi")

        testBasicService.call()
    }
}

@Scoped
class TestBasicService {
    fun call() {
        println("TestBasicService executed")
    }
}