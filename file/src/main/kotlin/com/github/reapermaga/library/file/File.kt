package com.github.reapermaga.library.file

import java.io.File

fun File.deleteRecursively() {
    if (isDirectory) {
        listFiles()?.forEach { it.deleteRecursively() }
    }
    delete()
}

fun File.createParentDirectories() {
    if (parentFile != null && !parentFile.exists()) {
        parentFile.mkdirs()
    }
}