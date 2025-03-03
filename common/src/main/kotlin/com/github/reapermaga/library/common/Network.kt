package com.github.reapermaga.library.common

import java.io.File
import java.net.URI

/**
 * Download a file from a URL and save it to a local path.
 *
 * @param url The URL to download the file from.
 * @param outputPath The path to save the file to.
 * @return The file that was downloaded.
 */
fun download(url: String, outputPath: String): File {
    val file = File(outputPath)
    println(file.absolutePath)
    file.parentFile?.mkdirs()
    file.createNewFile()
    file.writeBytes(URI.create(url).toURL().readBytes())
    return file
}

/**
 * Ping a URL to check if it is reachable.
 *
 * @param url The URL to ping.
 * @param timeout The timeout in milliseconds.
 * @return True if the URL is reachable, false otherwise.
 */
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

/**
 * Ping a host and port to check if it is reachable.
 *
 * @param host The host to ping.
 * @param port The port to ping.
 * @param timeout The timeout in milliseconds.
 * @return True if the host and port are reachable, false otherwise.
 */
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




