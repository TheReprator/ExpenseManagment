package dev.reprator.accountbook.api

import dev.reprator.core.inject.ApplicationScope
import me.tatarka.inject.annotations.Provides

actual interface AccountBookApiPlatformComponent {
    
    @Provides
    @ApplicationScope
    fun provideHttpClientEngine(): HttpClientEngine = Android.create()
}