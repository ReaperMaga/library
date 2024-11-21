package com.github.reapermaga.library.quarkus.exception

import com.github.reapermaga.library.quarkus.createRestResponse

data class ExceptionHttpResponse(val code : Int, val message : String)

fun ExceptionHttpResponse.toRestResponse() = createRestResponse<ExceptionHttpResponse>(this.code, this)