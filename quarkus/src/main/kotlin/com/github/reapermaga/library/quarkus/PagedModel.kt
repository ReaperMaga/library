package com.github.reapermaga.library.quarkus

data class PagedModel<T>(
    val totalCount: Long,
    val items: List<T>,
)
