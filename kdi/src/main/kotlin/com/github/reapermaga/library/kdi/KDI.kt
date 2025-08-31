package com.github.reapermaga.library.kdi

import com.github.reapermaga.library.kdi.processor.*

/**
 * Manager for the KDI system. This class is responsible for running the KDI system.
 *
 * @property entityRegistry Contains all entities that are registered in the KDI system.
 * @property processors They handle the logic of the annotations.
 */
class KDI {
    val entityRegistry: EntityRegistry = EntityRegistry()

    private val shutdownProcessor = ShutdownProcessor(entityRegistry)

    private val processors: MutableList<Processor> =
        mutableListOf(
            InjectProcessor(entityRegistry),
            StartupProcessor(entityRegistry),
            PostStartupProcessor(entityRegistry),
            shutdownProcessor,
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
    fun registerProcessor(processor: Processor) {
        this.processors.add(processor)
    }

    /**
     * Run the KDI system. It will process all entities and run the processors.
     *
     * @param entryPoint The entry point of the application. Mostly the main class.
     * @param packageName The package name to scan for entities.
     */
    fun run(
        entryPoint: Any,
        packageName: String,
    ) {
        entityRegistry.register(entryPoint)
        scan(entryPoint::class.java.classLoader, packageName).forEach {
            if (it.isAnnotationPresent(Scoped::class.java)) {
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

fun createKdiContext(
    entryPoint: Any,
    packageName: String,
    config: KDI.() -> Unit = {},
): KDI {
    val kdi = KDI()
    kdi.config()
    kdi.run(entryPoint, packageName)
    return kdi
}
