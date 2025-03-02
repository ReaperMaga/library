package com.github.reapermaga.library.quarkus

import jakarta.ws.rs.core.MediaType
import org.jboss.resteasy.reactive.RestResponse

fun <T> createRestResponse(status: Int, entity: T): RestResponse<T> {
    return RestResponse.ResponseBuilder.create<T>(status)
        .entity(entity)
        .type(MediaType.APPLICATION_JSON)
        .build()
}