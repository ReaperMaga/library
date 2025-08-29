package com.github.reapermaga.library.common

fun <I, O> createFilterPipeline(block: FilterPipeline<I, O>.() -> Unit): FilterPipeline<I, O> {
    val pipeline = FilterPipeline<I, O>()
    pipeline.block()
    return pipeline
}

fun <I, O> applyFilters(
    requests: List<FilterRequest<I>>,
    block: FilterPipeline<I, O>.() -> Unit
): O? {
    val pipeline = FilterPipeline<I, O>()
    pipeline.block()
    return pipeline.apply(requests)
}

class FilterPipeline<I, O> {

    private val filters = mutableListOf<Filter<I, O>>()

    fun addFilter(filter: Filter<I, O>) {
        filters.add(filter)
    }

    fun apply(request: List<FilterRequest<I>>): O? {
        var result: O? = null
        for (req in request) {
            val filter = filters.find { it.identifier == req.identifier }
            if (filter != null) {
                result = filter.filter(req.input, req.method)
            }
        }
        return result
    }

}

data class FilterRequest<I>(
    val identifier: String,
    val input: I,
    val method: FilterMethod
)

interface Filter<I, O> {

    val identifier: String

    fun filter(input: I, method: FilterMethod): O

}

enum class FilterMethod {
    EQUALS,
    CONTAINS,
    GTE,
    LTE
}