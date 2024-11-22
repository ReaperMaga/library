package com.github.reapermaga.library.cdi.processor

import com.github.reapermaga.library.cdi.CDIEntity
import com.github.reapermaga.library.cdi.CDIEntityRegistry

interface CDIProcessor {

    fun process(entity: CDIEntity)

    fun getPriority(): Int = 0
}

abstract class AbstractCDIProcessor : CDIProcessor {

    protected open val entityRegistry: CDIEntityRegistry = CDIEntityRegistry()
}