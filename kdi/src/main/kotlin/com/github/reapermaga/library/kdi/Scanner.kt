package com.github.reapermaga.library.kdi

import com.google.common.reflect.ClassPath

/**
 * Scans the classpath for classes in the given package. The package name should be the fully qualified package name.
 *
 * @param loader The class loader to use for scanning.
 * @param packageName The package name to scan for classes.
 */
internal fun scan(loader: ClassLoader, packageName: String): List<Class<*>> {
    val classes = mutableListOf<Class<*>>()
    val classPath = ClassPath.from(loader)
    classPath.topLevelClasses.forEach {
        if (it.name.startsWith(packageName.plus("."))) {
            classes.add(it.load())
        }
    }
    return classes
}