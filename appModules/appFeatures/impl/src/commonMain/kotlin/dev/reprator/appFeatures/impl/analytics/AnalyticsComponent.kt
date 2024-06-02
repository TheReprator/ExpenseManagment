package dev.reprator.appFeatures.impl.analytics

import dev.reprator.core.appinitializers.AppInitializer
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

expect interface AnalyticsPlatformComponent

interface AnalyticsComponent : AnalyticsPlatformComponent {
    @Provides
    @IntoSet
    fun provideAnalyticsInitializer(impl: AnalyticsInitializer): AppInitializer = impl
}
