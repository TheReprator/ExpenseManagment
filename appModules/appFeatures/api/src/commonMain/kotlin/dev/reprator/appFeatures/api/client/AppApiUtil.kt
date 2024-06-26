package dev.reprator.appFeatures.api.client

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

const val END_POINT = "0.0.0.0:8081"

suspend inline fun <reified T> HttpClient.safeRequest(
    block: HttpRequestBuilder.() -> Unit
): AppResult<T> =
    try {
        val response = request { block() }
        AppResult.Success(response.body())
    } catch (exception: ClientRequestException) {
        println("vikramTest:: ApiError:: ClientRequestException:: "+ exception.response.body())
        AppResult.Error.HttpError(
            code = exception.response.status.value,
            errorBody = exception.response.body(),
            errorMessage = "Status Code: ${exception.response.status.value} - API Key Missing",
        )
    } catch (exception: HttpExceptions) {
        println("vikramTest:: ApiError:: HttpExceptions:: "+ exception.response.body())
        AppResult.Error.HttpError(
            code = exception.response.status.value,
            errorBody = exception.response.body(),
            errorMessage = exception.message,
        )
    } catch (e: Exception) {
        println("vikramTest:: ApiError:: "+ e.message)
        AppResult.Error.GenericError(
            message = e.message,
            errorMessage = "Something went wrong",
        )
    }

class HttpExceptions(
    response: HttpResponse,
    failureReason: String?,
    cachedResponseText: String,
) : ResponseException(response, cachedResponseText) {
    override val message: String = "Status: ${response.status}" + " Failure: $failureReason"
}