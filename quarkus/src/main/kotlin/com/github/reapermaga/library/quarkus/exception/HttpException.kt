package com.github.reapermaga.library.quarkus.exception

import jakarta.ws.rs.core.Response

open class HttpException(
    val status: Response.Status,
    override val message: String,
) : RuntimeException(message)
