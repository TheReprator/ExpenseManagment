package dev.reprator.appFeatures.impl.analytics

import dev.reprator.appFeatures.api.analytics.AppAnalytics
import dev.reprator.core.inject.ApplicationScope
import me.tatarka.inject.annotations.Provides

actual interface AnalyticsPlatformComponent {
    @Provides
    @ApplicationScope
    fun provideAnalytics(): AppAnalytics = object : AppAnalytics {
        override fun trackScreenView(name: String, arguments: Map<String, *>?) = Unit
        override fun setEnabled(enabled: Boolean) = Unit
    }
}
