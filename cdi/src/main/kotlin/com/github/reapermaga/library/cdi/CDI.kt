package com.github.reapermaga.library.cdi

import com.github.reapermaga.library.cdi.processor.CDIProcessor
import com.github.reapermaga.library.cdi.processor.InjectProcessor
import com.github.reapermaga.library.cdi.processor.StartupProcessor

class CDI {

    private val entityRegistry: CDIEntityRegistry = CDIEntityRegistry()

    private val processors: MutableList<CDIProcessor> = mutableListOf(
        InjectProcessor(entityRegistry),
        StartupProcessor(entityRegistry)
    )

    fun registerProcessor(processor: CDIProcessor) {
        this.processors.add(processor)
    }

    fun run(entryPoint: Any, packageName: String) {
        entityRegistry.register(entryPoint)
        scan(entryPoint::class.java.classLoader, packageName).forEach {
            if(it.isAnnotationPresent(Scoped::class.java)) {
                entityRegistry.register(it)
            }
        }
        val processors = processors.sortedBy { it.getPriority() }
        processors.forEach { processor ->
            entityRegistry.entities.forEach { entity ->
                processor.process(entity)
            }
        }

    }
}

