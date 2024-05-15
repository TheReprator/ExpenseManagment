package dev.reprator.accountbook.settings

import androidx.compose.runtime.*
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import dev.reprator.appFeatures.api.preferences.AccountbookPreferences
import dev.reprator.core.app.ApplicationInfo
import dev.reprator.core.app.Flavor
import dev.reprator.screens.DevSettingsScreen
import dev.reprator.screens.SettingsScreen
import dev.reprator.screens.UrlScreen
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class SettingsUiPresenterFactory(
    private val presenterFactory: (Navigator) -> SettingsPresenter,
) : Presenter.Factory {
    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext,
    ): Presenter<*>? = when (screen) {
        is SettingsScreen -> presenterFactory(navigator)
        else -> null
    }
}

@Inject
class SettingsPresenter(
    @Assisted private val navigator: Navigator,
    settings: Lazy<AccountbookPreferences>,
    private val applicationInfo: ApplicationInfo,
) : Presenter<SettingsUiState> {
    private val settings by settings

    @Composable
    override fun present(): SettingsUiState {

        val themePreference = produceState(initialValue = AccountbookPreferences.Theme.SYSTEM) {
            settings.observeTheme()
        }

        val theme by remember { themePreference }

        val useDynamicColorPreference = produceState(initialValue = false) {
            settings.observeUseDynamicColors()
        }

        val useDynamicColors by remember { useDynamicColorPreference }

        val useLessDataPreference = produceState(initialValue = false) {
            settings.observeUseLessData()
        }

        val useLessData by remember { useLessDataPreference }

        val crashDataReportingEnabledPreference = produceState(initialValue = true) {
            settings.observeReportAppCrashes()
        }

        val crashDataReportingEnabled by remember { crashDataReportingEnabledPreference }

        val analyticsPreference = produceState(initialValue = true) {
            settings.observeReportAppCrashes()
        }

        val analyticsDataReportingEnabled by remember { analyticsPreference }

        val coroutineScope = rememberCoroutineScope()

        fun eventSink(event: SettingsUiEvent) {
            when (event) {
                SettingsUiEvent.NavigateUp -> {
                    navigator.pop()
                }

                is SettingsUiEvent.SetTheme -> {
                    coroutineScope.launch { settings.setTheme(event.theme) }
                }

                SettingsUiEvent.ToggleUseDynamicColors -> {
                    coroutineScope.launch { settings.toggleUseDynamicColors() }
                }

                SettingsUiEvent.ToggleUseLessData -> {
                    coroutineScope.launch { settings.toggleUseLessData() }
                }

                SettingsUiEvent.ToggleCrashDataReporting -> {
                    coroutineScope.launch { settings.toggleReportAppCrashes() }
                }

                SettingsUiEvent.ToggleAnalyticsDataReporting -> {
                    coroutineScope.launch { settings.toggleReportAnalytics() }
                }

                SettingsUiEvent.NavigatePrivacyPolicy -> {
                    navigator.goTo(UrlScreen("https://github.com/TheReprator"))
                }

                SettingsUiEvent.NavigateDeveloperSettings -> navigator.goTo(DevSettingsScreen)
            }
        }

        return SettingsUiState(
            theme = theme,
            useDynamicColors = useDynamicColors,
            dynamicColorsAvailable = DynamicColorsAvailable,
            useLessData = useLessData,
            crashDataReportingEnabled = crashDataReportingEnabled,
            analyticsDataReportingEnabled = analyticsDataReportingEnabled,
            applicationInfo = applicationInfo,
            showDeveloperSettings = applicationInfo.flavor == Flavor.Qa,
            eventSink = ::eventSink,
        )
    }
}
