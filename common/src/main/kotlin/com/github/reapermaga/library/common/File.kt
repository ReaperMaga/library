package com.github.reapermaga.library.common

import java.io.File

/**
 * Retrieves a [File] object corresponding to a resource file located in the classpath.
 *
 * This function uses the current thread's context class loader to locate the
 * resource by its name, and then converts the resource's URI into a [File] instance.
 *
 * @param fileName the name of the resource file to retrieve.
 * @return a [File] object representing the resource file.
 * @throws IllegalArgumentException if the resource file with the given name is not found.
 */
fun getFileFromResources(fileName: String): File {
    val classLoader = Thread.currentThread().contextClassLoader
    val resource = classLoader.getResource(fileName)
        ?: throw IllegalArgumentException("File not found: $fileName")
    return File(resource.toURI())
}