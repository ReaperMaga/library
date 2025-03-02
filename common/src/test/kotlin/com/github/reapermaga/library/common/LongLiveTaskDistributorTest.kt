package com.github.reapermaga.library.common

import com.github.reapermaga.library.common.concurrency.LongLiveTaskDistributor
import org.junit.jupiter.api.Test
import java.lang.Thread.sleep

class LongLiveTaskDistributorTest {

    @Test
    fun `Test common use-case`() {
        val distributor = LongLiveTaskDistributor<TestTask>(4, 500) {
            it.replaceRWithQuestionMark()
            println("${Thread.currentThread().name} ${it.text}")
        }
        distributor.addTask(TestTask("Rapid"))
        distributor.addTask(TestTask("Random"))
        distributor.addTask(TestTask("Randal"))
        distributor.addTask(TestTask("Rock"))
        distributor.addTask(TestTask("Rausch"))
        distributor.addTask(TestTask("Rocket"))
        sleep(5000)
        distributor.shutdown()
    }

    @Test
    fun `Test scaling`() {
        val distributor = LongLiveTaskDistributor<TestTask>(1, 500) {
            it.replaceRWithQuestionMark()
            println("${Thread.currentThread().name} ${it.text}")
        }
        distributor.addTask(TestTask("Rapid"))
        distributor.addTask(TestTask("Random"))
        distributor.addTask(TestTask("Randal"))
        distributor.addTask(TestTask("Rock"))
        distributor.addTask(TestTask("Rausch"))
        distributor.addTask(TestTask("Rocket"))
        sleep(2000)
        println("Scaling to 2 workers")
        distributor.scale(2)
        sleep(3000)
        distributor.shutdown()
    }

    class TestTask(var text: String) {

        fun replaceRWithQuestionMark() {
            text = text.replace("R", "?")
        }
    }

}