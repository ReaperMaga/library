package com.github.reapermaga.library.quarkus.exception

data class HttpException(val statusCode: Int, override val message: String) : RuntimeException(message)
