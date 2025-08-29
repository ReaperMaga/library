package com.github.reapermaga.library.common

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class FilterTest {

    @Test
    fun `Test filter`() {
        val requests = listOf(FilterRequest("TestFilter", "Test", FilterMethod.EQUALS))
        val result = applyFilters(requests) {
            addFilter(TestFilter())
        }
        Assertions.assertTrue(result == true)
    }
}

class TestFilter() : Filter<String, Boolean> {

    override val identifier: String = "TestFilter"

    override fun filter(input: String, method: FilterMethod): Boolean {
        val field = "Test"
        return when (method) {
            FilterMethod.EQUALS -> field == input
            FilterMethod.CONTAINS -> field.contains(input)
            FilterMethod.GTE -> field >= input
            FilterMethod.LTE -> field <= input
        }
    }
}