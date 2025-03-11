package com.github.reapermaga.library.quarkus.exception

import org.jboss.resteasy.reactive.server.ServerExceptionMapper

class HttpExceptionMapper {

    @ServerExceptionMapper
    fun mapHttpException(exception: HttpException) = ExceptionHttpResponse(exception.status.statusCode, exception.message).toRestResponse()

}