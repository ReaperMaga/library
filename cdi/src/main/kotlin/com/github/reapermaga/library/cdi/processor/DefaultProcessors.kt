package com.github.reapermaga.library.cdi.processor

import com.github.reapermaga.library.cdi.CDIEntity
import com.github.reapermaga.library.cdi.CDIEntityRegistry
import com.github.reapermaga.library.cdi.Inject
import com.github.reapermaga.library.cdi.PostStartup
import com.github.reapermaga.library.cdi.Shutdown
import com.github.reapermaga.library.cdi.Startup

class InjectProcessor(override val entityRegistry: CDIEntityRegistry) : AbstractCDIProcessor() {

    override fun process(entity: CDIEntity) {
        entity.instance::class.java.declaredFields.forEach { field ->
            if (field.isAnnotationPresent(Inject::class.java)) {
                field.trySetAccessible()
                val dependency = entityRegistry.getEntity(field.type)
                if (dependency != null) {
                    field.set(entity.instance, dependency.instance)
                }
            }
        }
    }


    override fun getPriority(): Int = 1
}

class PostStartupProcessor(override val entityRegistry: CDIEntityRegistry) : AbstractCDIProcessor() {

    override fun process(entity: CDIEntity) {
        entity.instance::class.java.declaredMethods.forEach { method ->
            if (method.isAnnotationPresent(PostStartup::class.java)) {
                method.trySetAccessible()
                method.invoke(entity.instance)
            }
        }
    }

    override fun getPriority(): Int = 5
}

class StartupProcessor(override val entityRegistry: CDIEntityRegistry) : AbstractCDIProcessor() {

    override fun process(entity: CDIEntity) {
        entity.instance::class.java.declaredMethods.forEach { method ->
            if (method.isAnnotationPresent(Startup::class.java)) {
                method.trySetAccessible()
                method.invoke(entity.instance)
            }
        }
    }

    override fun getPriority(): Int = 10
}

class ShutdownProcessor(override val entityRegistry: CDIEntityRegistry) : AbstractCDIProcessor() {

    private val methods = mutableListOf<() -> Unit>()

    override fun process(entity: CDIEntity) {
        entity.instance::class.java.declaredMethods.forEach { method ->
            if (method.isAnnotationPresent(Shutdown::class.java)) {
                method.trySetAccessible()
                methods.add {
                    method.invoke(entity.instance)
                }
            }
        }
    }

    fun invokeMethods() {
        methods.forEach {it.invoke() }
    }

    override fun getPriority(): Int = 10
}