package dev.reprator.appFeatures.impl.analytics

import dev.reprator.appFeatures.api.analytics.AppAnalytics
import dev.reprator.core.inject.ApplicationScope
import me.tatarka.inject.annotations.Provides

actual interface AnalyticsPlatformComponent {

    @ApplicationScope
    @Provides
    fun provideAccountBookFirebaseAnalytics(bind: AccountBookFirebaseAnalytics): AppAnalytics = bind
}
