package dev.reapermaga.library.quarkus.exception

import jakarta.validation.ConstraintViolationException
import org.jboss.resteasy.reactive.RestResponse
import org.jboss.resteasy.reactive.server.ServerExceptionMapper

class ViolationExceptionMapper {

    @ServerExceptionMapper
    fun mapViolationExceptions(exception : ConstraintViolationException) : RestResponse<ExceptionHttpResponse> {
        val errors = buildList<String> {
            exception.constraintViolations.forEach {
                val last = it.propertyPath.lastOrNull()
                if (last != null) {
                    add("${last.name} ${it.message}")
                }
            }
        }
        return ExceptionHttpResponse(400, errors.joinToString(", ")).toRestResponse()
    }
}