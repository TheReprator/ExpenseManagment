package dev.reprator.appFeatures.impl.utility

import me.tatarka.inject.annotations.Inject

@Inject
class InternetCheckerApplifeCycleInitializer(
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
