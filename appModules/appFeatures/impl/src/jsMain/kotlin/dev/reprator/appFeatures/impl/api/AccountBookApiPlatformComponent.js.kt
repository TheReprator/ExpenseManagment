package dev.reprator.appFeatures.impl.api

import dev.reprator.core.inject.ApplicationScope
import io.ktor.client.engine.*
import io.ktor.client.engine.js.*
import me.tatarka.inject.annotations.Provides

actual interface AccountBookApiPlatformComponent {
    
    @Provides
    @ApplicationScope
    fun provideHttpClientEngine(): HttpClientEngine = Js.create()
}