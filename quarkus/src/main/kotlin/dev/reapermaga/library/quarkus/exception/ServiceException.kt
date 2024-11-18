package dev.reapermaga.library.quarkus.exception

data class ServiceException(val statusCode: Int, override val message: String) : Exception(message)
