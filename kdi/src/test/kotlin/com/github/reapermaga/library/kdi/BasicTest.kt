package com.github.reapermaga.library.kdi

import org.junit.jupiter.api.Test

class BasicTest {

    @Inject
    lateinit var testBasicService: TestBasicService

    @Test
    fun testBasic() {
        createKdiContext(this, "com.github.reapermaga.library.kdi")
        testBasicService.call()
    }
}

@Scoped
class TestBasicService {
    fun call() {
        println("TestBasicService executed")
    }
}