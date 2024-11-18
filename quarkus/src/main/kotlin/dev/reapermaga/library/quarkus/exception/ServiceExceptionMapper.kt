package dev.reapermaga.library.quarkus.exception

import org.jboss.resteasy.reactive.server.ServerExceptionMapper

class ServiceExceptionMapper {

    @ServerExceptionMapper
    fun mapServiceException(exception: ServiceException) = ExceptionHttpResponse(exception.statusCode, exception.message).toRestResponse()

}