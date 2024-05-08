package dev.reprator.appFeatures.impl.logger

import dev.reprator.appFeatures.api.preferences.AccountbookPreferences
import dev.reprator.core.appinitializers.AppInitializer
import dev.reprator.core.inject.ApplicationCoroutineScope
import dev.reprator.core.util.AppCoroutineDispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
class CrashReportingInitializer(
    private val preferences: Lazy<AccountbookPreferences>,
    private val scope: ApplicationCoroutineScope,
    private val action: SetCrashReportingEnabledAction,
    private val dispatchers: AppCoroutineDispatchers,
) : AppInitializer {
    override fun initialize() {
        scope.launch {
            preferences.value.observeReportAppCrashes()
                .flowOn(dispatchers.io)
                .collect(action::invoke)
        }
    }
}
