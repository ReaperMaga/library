package com.github.reapermaga.library.common

import java.io.File
import java.io.InputStream
import java.net.HttpURLConnection
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
    file.parentFile?.mkdirs()
    file.createNewFile()
    file.writeBytes(URI.create(url).toURL().readBytes())
    return file
}


/**
 * Downloads content from the specified URL and returns it as an InputStream.
 *
 * @param url The URL to download the content from.
 * @param settingsBlock A lambda to configure optional download settings such as User-Agent.
 *                      The settings can be customized by modifying properties of the
 *                      [DownloadSettings] instance provided within the lambda.
 * @return An InputStream providing access to the downloaded content.
 */
fun download(url: String, settingsBlock: DownloadSettings.() -> Unit = {}): InputStream {
    val settings = DownloadSettings().apply {
        settingsBlock.invoke(this)
    }
    val connection = URI(url).toURL().openConnection() as HttpURLConnection
    connection.requestMethod = settings.requestMethod
    connection.connectTimeout = settings.timeout
    if (settings.agent != null) connection.setRequestProperty("User-Agent", settings.agent)
    return connection.inputStream
}

/**
 * Represents configuration settings for a download operation.
 *
 * @property agent The User-Agent string to be used for the download request.
 *                 Defaults to null, which means no specific User-Agent is set.
 *
 * @constructor Creates a new instance of DownloadSettings with an optional User-Agent.
 */
data class DownloadSettings(var requestMethod: String = "GET", var agent: String? = null, var timeout: Int = 5000) {

    fun useBrowserAgent() {
        agent =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36"
    }
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
fun ping(host: String, port: Int, timeout: Int = 3000): Boolean {
    return try {
        val socket = java.net.Socket()
        socket.connect(java.net.InetSocketAddress(host, port), timeout)
        socket.close()
        true
    } catch (e: Exception) {
        false
    }
}




