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

fun pingUrl(url: String, timeout: Int = 3000): Boolean {
    return try {
        val connection = URI(url).toURL().openConnection()
        connection.connectTimeout = timeout
        connection.connect()
        true
    } catch (e: Exception) {
        false
    }
}


fun ping(host: String, port: Int, timeout : Int = 3000): Boolean {
    return try {
        val socket = java.net.Socket()
        socket.connect(java.net.InetSocketAddress(host, port), timeout)
        socket.close()
        true
    } catch (e: Exception) {
        false
    }
}




