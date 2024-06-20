package dev.reprator.accountbook.api

import dev.reprator.accountbook.api.client.pluginClientDefaultRequest
import dev.reprator.core.inject.ApplicationScope
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.api.ClientPlugin
import io.ktor.http.HttpHeaders
import io.ktor.util.Attributes
import me.tatarka.inject.annotations.Provides

expect interface AccountBookApiPlatformComponent

interface AccountBookApiComponent : AccountBookApiPlatformComponent {

    @Provides
    @ApplicationScope
    fun provideHttpClientAttributes(engine: HttpClientEngine): Attributes = Attributes(true)


    @Provides
    @ApplicationScope
    fun provideHttpClient(engine: HttpClientEngine): HttpClient {

           return HttpClient(engine) {

                expectSuccess = true

                install(HttpTimeout) {
                    val MILLISECONDS = 1000L

                    connectTimeoutMillis = 10 * MILLISECONDS
                    socketTimeoutMillis = 10 * MILLISECONDS
                    requestTimeoutMillis = 10 * MILLISECONDS
                }

               pluginClientDefaultRequest()
            }

    }

    @Provides
    @ApplicationScope
    fun bindApiService(bind: AccountBookApiService): ApiService = bind

}
