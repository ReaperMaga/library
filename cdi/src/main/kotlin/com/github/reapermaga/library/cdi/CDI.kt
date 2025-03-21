package com.github.reapermaga.library.cdi

import com.github.reapermaga.library.cdi.processor.*

/**
 * Manager for the CDI system. This class is responsible for running the CDI system.
 *
 * @property entityRegistry Contains all entities that are registered in the CDI system.
 * @property processors They handle the logic of the annotations.
 */
class CDI {

    val entityRegistry: CDIEntityRegistry = CDIEntityRegistry()

    private val shutdownProcessor = ShutdownProcessor(entityRegistry)

    private val processors: MutableList<CDIProcessor> = mutableListOf(
        InjectProcessor(entityRegistry),
        StartupProcessor(entityRegistry),
        PostStartupProcessor(entityRegistry),
        shutdownProcessor
    )

    /**
     * Invokes all entity functions that are annotated with [Shutdown]
     */
    fun shutdown() {
        shutdownProcessor.invokeMethods()
    }

    /**
     * Register a processor
     * @param processor The processor to register
     */
    fun registerProcessor(processor: CDIProcessor) {
        this.processors.add(processor)
    }

    /**
     * Run the CDI system. It will process all entities and run the processors.
     *
     * @param entryPoint The entry point of the application. Mostly the main class.
     * @param packageName The package name to scan for entities.
     */
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

