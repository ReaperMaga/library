package com.github.reapermaga.library.common

import java.io.File
import java.net.URI

fun download(url: String, outputPath: String): File {
    val file = File(outputPath)
    println(file.absolutePath)
    file.parentFile?.mkdirs()
    file.createNewFile()
    file.writeBytes(URI.create(url).toURL().readBytes())
    return file
}


