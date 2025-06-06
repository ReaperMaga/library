package com.github.reapermaga.library.quarkus

import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import io.quarkus.arc.All
import io.quarkus.jackson.ObjectMapperCustomizer
import jakarta.inject.Singleton

class ObjectMapperConfiguration {

    @Singleton
    fun objectMapper(@All customizers : MutableList<ObjectMapperCustomizer>) : ObjectMapper {
        val mapper = JsonMapper.builder()
            .configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true)
            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)
            .build()
        for (customizer in customizers) {
            customizer.customize(mapper)
        }
        return mapper
    }
}