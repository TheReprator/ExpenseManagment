package dev.reprator.appFeatures.impl.preferences

import dev.reprator.core.util.AppCoroutineDispatchers
import dev.reprator.appFeatures.api.preferences.AccountbookPreferences
import dev.reprator.appFeatures.api.preferences.AccountbookPreferences.Theme
import io.github.xxfast.kstore.KStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import me.tatarka.inject.annotations.Inject

@Inject
class AccountbookPreferencesImpl(
    private val settings: Lazy<KStore<AccountBookSetting>>,
    private val dispatchers: AppCoroutineDispatchers,
) : AccountbookPreferences {

    override suspend fun setTheme(theme: Theme) = withContext(dispatchers.io) {
        settings.value.update {
            it?.copy(theme = theme)
        }
    }

    override suspend fun observeTheme(): Flow<Theme> {
        return flowOf(settings.value.get()?.theme ?: Theme.SYSTEM)
    }

    override suspend fun toggleUseDynamicColors() = withContext(dispatchers.io) {
        settings.value.update {
            it?.copy(dynamicColorEnable = !it.dynamicColorEnable)
        }
    }

    override suspend fun observeUseDynamicColors(): Flow<Boolean> =
        flowOf(settings.value.get()?.dynamicColorEnable ?: true)

    override suspend fun toggleUseLessData() = withContext(dispatchers.io) {
        settings.value.update {
            it?.copy(shouldUseLessData = !it.shouldUseLessData)
        }
    }

    override suspend fun observeUseLessData() = flowOf(settings.value.get()?.shouldUseLessData ?: true)

    override suspend fun getUseLessData(): Boolean = withContext(dispatchers.io) {
        settings.value.get()?.shouldUseLessData ?: true
    }

    override suspend fun toggleReportAppCrashes() {
        withContext(dispatchers.io) {
            settings.value.update {
                it?.copy(crashEnabled = !it.crashEnabled)
            }
        }
    }

    override suspend fun observeReportAppCrashes(): Flow<Boolean> {
        return flowOf(settings.value.get()?.crashEnabled ?: true)
    }

    override suspend fun toggleReportAnalytics() {
        withContext(dispatchers.io) {
            settings.value.update {
                it?.copy(analyticsEnabled = !it.analyticsEnabled)
            }
        }
    }

    override suspend fun observeReportAnalytics(): Flow<Boolean> {
        return flowOf(settings.value.get()?.analyticsEnabled!!)
    }

}

@Serializable
data class AccountBookSetting(
    val theme: Theme,
    val dynamicColorEnable: Boolean,
    val crashEnabled: Boolean,
    val analyticsEnabled: Boolean,
    val shouldUseLessData: Boolean
) {
    constructor() : this(
        Theme.SYSTEM, false,
        true, true, false
    )
}
