package dev.reprator.accountbook.utility.httpPlugin

import co.touchlab.kermit.Logger
import dev.reprator.accountbook.cleanArchitecture.dataSource.remote.ApiService.Companion.END_POINT
import io.ktor.client.*
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.header
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

const val TIMEOUT_DURATION: Long = 60_000

fun appHttpClient(
    json: Json,
    httpClientEngine: HttpClientEngine,
) = HttpClient(httpClientEngine) {

        expectSuccess = true
       // install(DataTransformationPlugin)

        install(ContentNegotiation) { json(json = json) }

        install(Logging) {
            logger = CustomHttpLogger()
            level = LogLevel.ALL
        }

        pluginClientResponseValidator(this)

        install(DefaultRequest) {
            header(HttpHeaders.Accept, ContentType.Application.Json)
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            url {
                protocol = URLProtocol.HTTP
                host = END_POINT
            }
        }

        install(HttpTimeout) {
            requestTimeoutMillis = TIMEOUT_DURATION
            connectTimeoutMillis = TIMEOUT_DURATION
            socketTimeoutMillis = TIMEOUT_DURATION
        }

    }

fun pluginClientResponseValidator(httpClientConfig: HttpClientConfig<*>) {

    httpClientConfig.HttpResponseValidator {
        validateResponse { response ->

            if (!response.status.isSuccess()) {
                val reason = when (response.status) {
                    HttpStatusCode.Unauthorized -> "Unauthorized request"
                    HttpStatusCode.Forbidden -> "${response.status.value} Missing API key."
                    HttpStatusCode.NotFound -> "Invalid Request"
                    HttpStatusCode.UpgradeRequired -> "Upgrade to VIP"
                    HttpStatusCode.RequestTimeout -> "Network Timeout"
                    in HttpStatusCode.InternalServerError..HttpStatusCode.GatewayTimeout ->
                        "${response.status.value} Server Error"

                    else -> "Network error!"
                }

                Logger.d("vikramTest:: validateResponse::")  { reason }

                throw mapResponse(response, reason)
            }
        }

        handleResponseExceptionWithRequest { exception, _ ->
            val clientException =
                exception as? ClientRequestException ?: return@handleResponseExceptionWithRequest

            val exceptionResponse = clientException.response

            Logger.d( "vikramTest:: handleResponseExceptionWithRequest::") { exceptionResponse.toString() }
            throw mapResponse(exceptionResponse)
        }
    }
}

suspend fun mapResponse(response: HttpResponse, failureReason: String ?= null): Exception {

    val (message, statusCode) = try {
        val body = Json.decodeFromString<FailDTOResponse>(response.bodyAsText())
        body.statusMessage to body.statusCode
    }catch (e: Exception) {
        println(e)
        response.bodyAsText() to response.status.value
    }


    return AppErrorException(
        message = failureReason ?: message,
        statusCode = statusCode,
    )
}

class CustomHttpLogger : io.ktor.client.plugins.logging.Logger {
    override fun log(message: String) {
        Logger.d("loggerTag") {
            message
        }
    }
}