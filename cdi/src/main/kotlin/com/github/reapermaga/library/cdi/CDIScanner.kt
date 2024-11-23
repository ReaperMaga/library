package com.github.reapermaga.library.cdi

import com.google.common.reflect.ClassPath

fun scan(loader : ClassLoader, packageName: String): List<Class<*>> {
    val classes = mutableListOf<Class<*>>()
    val classPath = ClassPath.from(loader)
    classPath.topLevelClasses.forEach {
        if (it.name.startsWith(packageName.plus("."))) {
            classes.add(it.load())
        }
    }
    return classes
}