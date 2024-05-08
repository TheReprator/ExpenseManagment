package dev.reprator.appFeatures.impl.analytics

import dev.reprator.appFeatures.api.analytics.Analytics
import dev.reprator.appFeatures.api.preferences.AccountbookPreferences
import dev.reprator.core.appinitializers.AppInitializer
import dev.reprator.core.inject.ApplicationCoroutineScope
import dev.reprator.core.util.AppCoroutineDispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
class AnalyticsInitializer(
    private val preferences: Lazy<AccountbookPreferences>,
    private val scope: ApplicationCoroutineScope,
    private val analytics: Analytics,
    private val dispatchers: AppCoroutineDispatchers,
) : AppInitializer {
    override fun initialize() {
        scope.launch {
            preferences.value
                .observeReportAnalytics()
                .flowOn(dispatchers.io)
                .collect { enabled -> analytics.setEnabled(enabled) }
        }
    }
}
