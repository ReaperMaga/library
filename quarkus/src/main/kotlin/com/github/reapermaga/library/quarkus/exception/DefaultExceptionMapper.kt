package com.github.reapermaga.library.quarkus.exception

import io.quarkus.logging.Log
import jakarta.ws.rs.NotFoundException
import org.jboss.resteasy.reactive.RestResponse
import org.jboss.resteasy.reactive.server.ServerExceptionMapper

class DefaultExceptionMapper {

    @ServerExceptionMapper
    fun mapNotFoundException(exception : NotFoundException) =
        ExceptionHttpResponse(404, exception.message ?: "Resource not found").toRestResponse()

    @ServerExceptionMapper
    fun mapUnhandledException(exception : Exception): RestResponse<ExceptionHttpResponse> {
        Log.error("Unhandled exception: ${exception::class.simpleName}", exception)
        return ExceptionHttpResponse(500, exception.message ?: "There was an server error").toRestResponse()
    }
}