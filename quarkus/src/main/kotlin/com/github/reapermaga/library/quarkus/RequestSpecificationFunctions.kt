package com.github.reapermaga.library.quarkus

import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import io.restassured.response.ValidatableResponse
import kotlin.apply
import kotlin.let

fun postSpec(
    url: String = "",
    body: Any? = null,
    expectStatusCode: Int = 200,
    pathParams: Map<String, Any> = emptyMap(),
): ValidatableResponse =
    given()
        .apply { body?.let { contentType(ContentType.JSON).body(body) } }
        .`when`()
        .pathParams(pathParams)
        .post(url)
        .then()
        .statusCode(expectStatusCode)

fun getSpec(
    url: String = "",
    expectStatusCode: Int = 200,
    pathParams: Map<String, Any> = emptyMap(),
): ValidatableResponse =
    given()
        .`when`()
        .pathParams(pathParams)
        .get(url)
        .then()
        .statusCode(expectStatusCode)

fun deleteSpec(
    url: String = "",
    expectStatusCode: Int = 200,
    pathParams: Map<String, Any> = emptyMap(),
): ValidatableResponse =
    given()
        .`when`()
        .pathParams(pathParams)
        .delete(url)
        .then()
        .statusCode(expectStatusCode)

fun patchSpec(
    url: String = "",
    body: Any,
    expectStatusCode: Int = 200,
    pathParams: Map<String, Any> = emptyMap(),
): ValidatableResponse =
    given()
        .contentType(ContentType.JSON)
        .body(body)
        .`when`()
        .pathParams(pathParams)
        .patch(url)
        .then()
        .statusCode(expectStatusCode)
