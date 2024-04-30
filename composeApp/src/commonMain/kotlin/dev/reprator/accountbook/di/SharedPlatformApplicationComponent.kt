package dev.reprator.accountbook.di

import co.touchlab.kermit.Logger
import co.touchlab.kermit.NoTagFormatter
import co.touchlab.kermit.loggerConfigInit
import co.touchlab.kermit.platformLogWriter
import dev.reprator.accountbook.utility.base.AppCoroutineDispatchers
import me.tatarka.inject.annotations.Provides
import dev.reprator.accountbook.utility.httpPlugin.appHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import kotlinx.coroutines.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

expect interface SharedPlatformApplicationComponent

interface SharedApplicationComponent : SharedPlatformApplicationComponent, SplashComponent,
    ImageLoadingComponent {

    @OptIn(ExperimentalSerializationApi::class)
    @ApplicationScope
    @Provides
    fun provideJson(): Json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        useAlternativeNames = false
        explicitNulls = false
    }

    @ApplicationScope
    @Provides
    fun provideHttpClient(
        json: Json,
        httpClientEngine: HttpClientEngine
    ): HttpClient = appHttpClient(
        json = json,
        httpClientEngine = httpClientEngine
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @ApplicationScope
    @Provides
    fun provideCoroutineDispatchers(): AppCoroutineDispatchers = AppCoroutineDispatchers(
        io = Dispatchers.IO,
        databaseWrite = Dispatchers.IO.limitedParallelism(1),
        databaseRead = Dispatchers.IO.limitedParallelism(4),
        computation = Dispatchers.Default,
        main = Dispatchers.Main,
    )

    @ApplicationScope
    @Provides
    fun provideApplicationCoroutineScope(
        dispatchers: AppCoroutineDispatchers,
    ): ApplicationCoroutineScope = CoroutineScope(dispatchers.main + SupervisorJob())


    @ApplicationScope
    @Provides
    fun provideApplicationLogger(): Logger = Logger(loggerConfigInit(platformLogWriter(NoTagFormatter)))
}