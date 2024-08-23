package dev.reprator.appFeatures.impl.api

import dev.reprator.appFeatures.api.logger.Logger
import dev.reprator.appFeatures.impl.api.plugins.pluginClientDefaultRequest
import dev.reprator.core.inject.ApplicationScope
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.util.Attributes
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Provides

expect interface AccountBookApiPlatformComponent

interface AccountBookApiComponent : AccountBookApiPlatformComponent {

    @Provides
    @ApplicationScope
    fun provideHttpClientAttributes(engine: HttpClientEngine): Attributes = Attributes(true)


    @Provides
    @ApplicationScope
    fun provideHttpClient(engine: HttpClientEngine, appLogger: Logger): HttpClient {

           return HttpClient(engine) {

                expectSuccess = false

               install(ContentNegotiation) {
                   json(
                       Json {
                           prettyPrint = true
                           isLenient = true
                           useAlternativeNames = true
                           ignoreUnknownKeys = true
                           encodeDefaults = false
                       }
                   )
               }

                install(HttpTimeout) {
                    val MILLISECONDS = 1000L

                    connectTimeoutMillis = 10 * MILLISECONDS
                    socketTimeoutMillis = 10 * MILLISECONDS
                    requestTimeoutMillis = 10 * MILLISECONDS
                }

               install(Logging) {
                   logger = object : Logger, io.ktor.client.plugins.logging.Logger {
                       override fun log(message: String) {
                           appLogger.d { "Logger Ktor => $message" }
                       }
                   }
                   level = LogLevel.ALL
               }

               install(ResponseObserver) {
                   onResponse { response ->
                       appLogger.d { "HTTP status: ${response.status.value}"}
                   }
               }

               install(DefaultRequest) {
                   header(HttpHeaders.ContentType, ContentType.Application.Json)
               }

               pluginClientDefaultRequest()
            }

    }

}
