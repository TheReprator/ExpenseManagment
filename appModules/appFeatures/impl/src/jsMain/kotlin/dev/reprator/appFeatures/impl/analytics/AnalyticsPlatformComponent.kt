package dev.reprator.appFeatures.impl.analytics

import dev.reprator.appFeatures.api.analytics.Analytics
import dev.reprator.core.inject.ApplicationScope
import me.tatarka.inject.annotations.Provides

actual interface AnalyticsPlatformComponent {
    @get:Provides
    @get:ApplicationScope
    val analytics: Analytics
}
