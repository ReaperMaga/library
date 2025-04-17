package com.github.reapermaga.library.quarkus

import io.quarkus.smallrye.openapi.OpenApiFilter
import org.eclipse.microprofile.openapi.OASFilter
import org.eclipse.microprofile.openapi.models.media.Schema
import kotlin.collections.filterKeys
import kotlin.collections.forEach
import kotlin.collections.map
import kotlin.text.contains

@OpenApiFilter(OpenApiFilter.RunStage.BUILD)
class DelegateOpenApiFilter : OASFilter {
    override fun filterSchema(schema: Schema): Schema {
        if (schema.properties == null) return schema
        schema.properties.filterKeys { it.contains("delegate") }.map { it.key }.forEach { key ->
            schema.removeProperty(key)
        }
        return schema
    }
}
