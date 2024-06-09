package dev.reprator.accountbook.api

import dev.reprator.core.inject.ApplicationScope
import io.ktor.client.engine.*
import io.ktor.client.engine.java.*
import me.tatarka.inject.annotations.Provides

actual interface AccountBookApiPlatformComponent {
    
    @Provides
    @ApplicationScope
    fun provideHttpClientEngine(): HttpClientEngine = Java.create()
}