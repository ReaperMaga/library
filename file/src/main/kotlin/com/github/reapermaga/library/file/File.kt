package com.github.reapermaga.library.file

import java.io.File

/**
 * Deletes the file or directory and all its contents recursively.
 */
fun File.deleteRecursively() {
    if (isDirectory) {
        listFiles()?.forEach { it.deleteRecursively() }
    }
    delete()
}

/**
 * Creates the parent directories of the file if they do not exist.
 */
fun File.createParentDirectories() {
    if (parentFile != null && !parentFile.exists()) {
        parentFile.mkdirs()
    }
}
