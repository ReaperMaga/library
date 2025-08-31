package com.github.reapermaga.library.kdi.processor

import com.github.reapermaga.library.kdi.Entity
import com.github.reapermaga.library.kdi.EntityRegistry

/**
 * Interface for processors. They handle the logic of the annotations.
 */
interface Processor {
    fun process(entity: Entity)

    fun getPriority(): Int = 0
}

abstract class AbstractProcessor : Processor {
    protected open val entityRegistry: EntityRegistry = EntityRegistry()
}
