package dev.reprator.appFeatures.impl.preferences

import dev.reprator.core.util.AppCoroutineDispatchers
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.coroutines.getStringFlow
import com.russhwolf.settings.coroutines.toFlowSettings
import com.russhwolf.settings.get
import dev.reprator.appFeatures.api.preferences.AccountbookPreferences
import dev.reprator.appFeatures.api.preferences.AccountbookPreferences.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject

@OptIn(ExperimentalSettingsApi::class)
@Inject
class AccountbookPreferencesImpl(
    settings: Lazy<ObservableSettings>,
    private val dispatchers: AppCoroutineDispatchers,
) : AccountbookPreferences {
    private val settings: ObservableSettings by settings
    private val flowSettings by lazy { settings.value.toFlowSettings(dispatchers.io) }

    override suspend fun setTheme(theme: AccountbookPreferences.Theme) = withContext(dispatchers.io) {
        settings.putString(KEY_THEME, theme.storageKey)
    }

    override fun observeTheme(): Flow<Theme> {
        return settings.getStringFlow(KEY_THEME, THEME_SYSTEM_VALUE)
            .map(::getThemeForStorageValue)
    }

    override suspend fun toggleUseDynamicColors() = withContext(dispatchers.io) {
        settings.toggleBoolean(KEY_USE_DYNAMIC_COLORS, true)
    }

    override fun observeUseDynamicColors(): Flow<Boolean> {
        return flowSettings.getBooleanFlow(KEY_USE_DYNAMIC_COLORS, true)
    }

    override suspend fun getUseLessData(): Boolean = withContext(dispatchers.io) {
        settings[KEY_DATA_SAVER, false]
    }

    override suspend fun toggleUseLessData() = withContext(dispatchers.io) {
        settings.toggleBoolean(KEY_DATA_SAVER)
    }

    override fun observeUseLessData(): Flow<Boolean> {
        return flowSettings.getBooleanFlow(KEY_DATA_SAVER, false)
    }

    override suspend fun toggleReportAppCrashes() {
        withContext(dispatchers.io) {
            settings.toggleBoolean(KEY_OPT_IN_CRASH_REPORTING, true)
        }
    }

    override fun observeReportAppCrashes(): Flow<Boolean> {
        return flowSettings.getBooleanFlow(KEY_OPT_IN_CRASH_REPORTING, true)
    }

    override suspend fun toggleReportAnalytics() {
        withContext(dispatchers.io) {
            settings.toggleBoolean(KEY_OPT_IN_ANALYTICS_REPORTING, true)
        }
    }

    override fun observeReportAnalytics(): Flow<Boolean> {
        return flowSettings.getBooleanFlow(KEY_OPT_IN_ANALYTICS_REPORTING, true)
    }

}

private val Theme.storageKey: String
    get() = when (this) {
        Theme.LIGHT -> THEME_LIGHT_VALUE
        Theme.DARK -> THEME_DARK_VALUE
        Theme.SYSTEM -> THEME_SYSTEM_VALUE
    }

private fun getThemeForStorageValue(value: String) = when (value) {
    THEME_LIGHT_VALUE -> Theme.LIGHT
    THEME_DARK_VALUE -> Theme.DARK
    else -> Theme.SYSTEM
}

internal const val KEY_THEME = "pref_theme"
internal const val KEY_USE_DYNAMIC_COLORS = "pref_dynamic_colors"
internal const val KEY_DATA_SAVER = "pref_data_saver"

internal const val KEY_OPT_IN_CRASH_REPORTING = "pref_opt_in_crash_reporting"
internal const val KEY_OPT_IN_ANALYTICS_REPORTING = "pref_opt_in_analytics_reporting"

internal const val THEME_LIGHT_VALUE = "light"
internal const val THEME_DARK_VALUE = "dark"
internal const val THEME_SYSTEM_VALUE = "system"

private fun ObservableSettings.toggleBoolean(key: String, defaultValue: Boolean = false) {
    putBoolean(key, !getBoolean(key, defaultValue))
}
