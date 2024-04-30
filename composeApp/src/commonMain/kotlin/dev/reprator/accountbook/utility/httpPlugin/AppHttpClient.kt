package dev.reprator.accountbook.utility.httpPlugin

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.api.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.header
import io.ktor.client.statement.*
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

const val TIMEOUT_DURATION: Long = 60_000

@OptIn(ExperimentalSerializationApi::class)
fun appHttpClient(
    json: Json,
    httpClientEngine: HttpClientEngine,
) =
    HttpClient(httpClientEngine) {

        install(ContentNegotiation) { json(json = json) }

        install(Logging) {
            logger = CustomHttpLogger()
            level = LogLevel.ALL
        }
        
        install(CustomLoggerPlugin)

        install(DefaultRequest) {
            header(HttpHeaders.Accept, ContentType.Application.Json)
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }

        install(HttpTimeout) {
            requestTimeoutMillis = TIMEOUT_DURATION
            connectTimeoutMillis = TIMEOUT_DURATION
            socketTimeoutMillis = TIMEOUT_DURATION
        }


    }


private val CustomLoggerPlugin = createClientPlugin("CustomLoggerPlugin") {
    onRequest { request, content ->
        Logger.d("LoggerPlugin"){ "=============REQUEST==============" }
        Logger.d("LoggerPlugin"){ "${request.method.value} => ${request.url}" }
        Logger.d("LoggerPlugin"){ "BODY => ${request.body}" }
        Logger.d("LoggerPlugin"){ "=============END-REQUEST==============" }
    }

    onResponse {response ->
        Logger.d("LoggerPlugin"){ "=============RESPONSE==============" }
        Logger.d("LoggerPlugin"){ "${response.request.method.value} / ${response.status} => ${response.request.url}" }
        Logger.d("LoggerPlugin"){ "BODY => $response" }
        Logger.d("LoggerPlugin"){ "=============END-RESPONSE==============" }
    }
}

class CustomHttpLogger : io.ktor.client.plugins.logging.Logger {
    override fun log(message: String) {
        Logger.d("loggerTag") {
            message
        }
    }
}