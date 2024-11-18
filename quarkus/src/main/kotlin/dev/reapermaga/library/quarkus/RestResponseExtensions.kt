package dev.reapermaga.library.quarkus

import org.jboss.resteasy.reactive.RestResponse

fun <T> createRestResponse(status: Int, entity: T): RestResponse<T> {
    return RestResponse.ResponseBuilder.create<T>(status)
        .entity(entity)
        .build()
}