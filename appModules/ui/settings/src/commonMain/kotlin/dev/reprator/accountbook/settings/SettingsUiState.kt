package dev.reprator.accountbook.settings

import androidx.compose.runtime.Immutable
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import dev.reprator.appFeatures.api.preferences.AccountbookPreferences
import dev.reprator.core.app.ApplicationInfo

@Immutable
data class SettingsUiState(
    val theme: AccountbookPreferences.Theme,
    val dynamicColorsAvailable: Boolean,
    val useDynamicColors: Boolean,
    val useLessData: Boolean,
    val crashDataReportingEnabled: Boolean,
    val analyticsDataReportingEnabled: Boolean,
    val applicationInfo: ApplicationInfo,
    val showDeveloperSettings: Boolean,
    val eventSink: (SettingsUiEvent) -> Unit,
) : CircuitUiState

sealed interface SettingsUiEvent : CircuitUiEvent {
    data object NavigateUp : SettingsUiEvent
    data object NavigatePrivacyPolicy : SettingsUiEvent
    data object NavigateDeveloperSettings : SettingsUiEvent
    data object ToggleUseDynamicColors : SettingsUiEvent
    data object ToggleUseLessData : SettingsUiEvent
    data object ToggleCrashDataReporting : SettingsUiEvent
    data object ToggleAnalyticsDataReporting : SettingsUiEvent
    data class SetTheme(val theme: AccountbookPreferences.Theme) : SettingsUiEvent
}
