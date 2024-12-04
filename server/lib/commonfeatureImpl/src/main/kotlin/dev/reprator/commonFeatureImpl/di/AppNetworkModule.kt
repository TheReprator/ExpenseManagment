package dev.reprator.commonFeatureImpl.di

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dev.reprator.commonFeatureImpl.plugin.client.pluginClientDefaultRequest
import dev.reprator.commonFeatureImpl.plugin.client.pluginClientResponseAuth
import dev.reprator.commonFeatureImpl.plugin.client.pluginClientResponseValidator
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.api.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.config.*
import io.ktor.util.*
import org.koin.core.Koin
import org.koin.core.annotation.*

@Module
class AppNetworkModule {

    @Factory
    @Named(APP_PLUGIN_CUSTOM_LIST)
    fun myComponent(): List<ClientPlugin<Unit>> = emptyList()

    @Single
    fun objectMapper(): ObjectMapper = jacksonObjectMapper().apply {
        disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

        configure(SerializationFeature.INDENT_OUTPUT, true)
        setDefaultPrettyPrinter(DefaultPrettyPrinter().apply {
            indentArraysWith(DefaultPrettyPrinter.FixedSpaceIndenter.instance)
        })
    }

    @Single
    fun httpClientEngine(): HttpClientEngine = CIO.create {
        maxConnectionsCount = 1000

        endpoint {
            maxConnectionsPerRoute = 100
            pipelineMaxSize = 20
            keepAliveTime = 5000
            connectTimeout = 5000
            connectAttempts = 5
        }
    }

    @Single
    fun appClientAttributes(config : ApplicationConfig): Attributes =  Attributes(true)


    @Single
    fun appHttpClient(mapper: ObjectMapper, engine : HttpClientEngine,
                      koin: Koin, @Named(APP_PLUGIN_CUSTOM_LIST) engineList: List<ClientPlugin<Unit>>): HttpClient  {

        val client = HttpClient(engine) {

            expectSuccess = true

            engineList.forEach {
                install(it)
            }

            install(Logging) {

                logger = Logger.DEFAULT
                level = LogLevel.ALL
                sanitizeHeader { header -> header == HttpHeaders.Authorization }
            }

            install(ContentNegotiation) {
                jackson {
                    mapper
                }
            }


            install(HttpTimeout) {
                connectTimeoutMillis = 10 * MILLISECONDS
                socketTimeoutMillis = 10 * MILLISECONDS
                requestTimeoutMillis = 10 * MILLISECONDS
            }

            pluginClientDefaultRequest(koin, this)
            pluginClientResponseAuth(koin, this)
            pluginClientResponseValidator(koin, this)
        }
        return client
    }
}

/*
fun Application.configureDI(): org.koin.core.module.Module {
    install(KoinIsolated)
    val ktor = module(createdAtStart = true) {
        single { KtorServerExtension.TEST_SERVER!!.environment } bind ApplicationEnvironment::class
    }

    return ktor
}*/
