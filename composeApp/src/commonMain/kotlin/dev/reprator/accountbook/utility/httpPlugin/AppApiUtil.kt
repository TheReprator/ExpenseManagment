package dev.reprator.accountbook.utility.httpPlugin

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*

suspend inline fun <reified T> HttpClient.safeRequest(
    block: HttpRequestBuilder.() -> Unit
): Result<T> =
    try {
        val response = request { block() }
        Result.success(response.body())
    } catch (exception: ClientRequestException) {
        println("vikramTest:: ApiError:: ClientRequestException:: "+ exception.response.body())
        Result.failure(AppErrorException(message="my ClientRequestException"))
    } catch (e: Exception) {
        println("vikramTest:: ApiError:: "+ e.message)
        Result.failure(AppErrorException(message="Something went wrong"))
    }


class AppErrorException(
    val statusCode: Int =-1, override val message: String 
) : Exception() 