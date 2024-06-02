package dev.reprator.accountbook.settings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoMode
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import dev.reprator.appFeatures.api.preferences.AccountbookPreferences
import dev.reprator.screens.SettingsScreen
import me.tatarka.inject.annotations.Inject

@Inject
class SettingsUiFactory : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
        is SettingsScreen -> {
            ui<SettingsUiState> { state, modifier ->
                Settings(state, modifier)
            }
        }

        else -> null
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun Settings(
    state: SettingsUiState,
    modifier: Modifier = Modifier,
) {
    // Need to extract the eventSink out to a local val, so that the Compose Compiler
    // treats it as stable. See: https://issuetracker.google.com/issues/256100927
    val eventSink = state.eventSink

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("settings Title") },
                navigationIcon = {
                    IconButton(onClick = { eventSink(SettingsUiEvent.NavigateUp) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
            )
        },
        modifier = modifier,
    ) { contentPadding ->
        LazyColumn(
            contentPadding = contentPadding,
            modifier = Modifier.fillMaxWidth(),
        ) {
            stickyHeader {
                PreferenceHeader("Category Title")
            }

            item {
                ThemePreference(
                    title = "Theme Title",
                    selected = state.theme,
                    onThemeSelected = { eventSink(SettingsUiEvent.SetTheme(it)) },
                )
            }

            item { PreferenceDivider() }

            if (state.dynamicColorsAvailable) {
                item {
                    CheckboxPreference(
                        title = "DynamicColor Title",
                        summaryOff = "DynamicColor Summary",
                        onCheckClicked = { eventSink(SettingsUiEvent.ToggleUseDynamicColors) },
                        checked = state.useDynamicColors,
                    )
                }

                item { PreferenceDivider() }
            }

            item {
                CheckboxPreference(
                    title = "DataSaver Title",
                    summaryOff = "DataSaverSummary Off",
                    summaryOn = "DataSaverSummary On",
                    onCheckClicked = { eventSink(SettingsUiEvent.ToggleUseLessData) },
                    checked = state.useLessData,
                )
            }

            item { PreferenceDivider() }

            stickyHeader {
                PreferenceHeader("Privacy Category Title")
            }

            item {
                Preference(
                    title = "Privacy Policy",
                    modifier = Modifier.clickable {
                        eventSink(SettingsUiEvent.NavigatePrivacyPolicy)
                    },
                )
            }

            item { PreferenceDivider() }

            item {
                CheckboxPreference(
                    title = "Crash Dat aCollection Title",
                    summaryOff = "Crash Data Collection Summary",
                    onCheckClicked = { eventSink(SettingsUiEvent.ToggleCrashDataReporting) },
                    checked = state.crashDataReportingEnabled,
                )
            }

            item { PreferenceDivider() }

            item {
                CheckboxPreference(
                    title = "AnalyticsmData Collection Title",
                    summaryOff = "AnalyticsmData Collection Summary",
                    onCheckClicked = { eventSink(SettingsUiEvent.ToggleAnalyticsDataReporting) },
                    checked = state.analyticsDataReportingEnabled,
                )
            }

            itemSpacer(24.dp)

            stickyHeader {
                PreferenceHeader("About Category Title")
            }

            item {
                Preference(
                    title = "App Version",
                    summary = {
                        Text(
                            text = "versionName versionCode"
                        )
                    },
                )
            }

            if (state.showDeveloperSettings) {
                item { PreferenceDivider() }

                item {
                    Preference(
                        title = "Settings Title",
                        modifier = Modifier.clickable {
                            eventSink(SettingsUiEvent.NavigateDeveloperSettings)
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun ThemePreference(
    selected: AccountbookPreferences.Theme,
    onThemeSelected: (AccountbookPreferences.Theme) -> Unit,
    title: String,
    modifier: Modifier = Modifier,
) {
    Preference(
        title = title,
        control = {
            Row(Modifier.selectableGroup()) {
                ThemeButton(
                    icon = Icons.Default.AutoMode,
                    onClick = { onThemeSelected(AccountbookPreferences.Theme.SYSTEM) },
                    isSelected = selected == AccountbookPreferences.Theme.SYSTEM,
                )

                ThemeButton(
                    icon = Icons.Default.LightMode,
                    onClick = { onThemeSelected(AccountbookPreferences.Theme.LIGHT) },
                    isSelected = selected == AccountbookPreferences.Theme.LIGHT,
                )

                ThemeButton(
                    icon = Icons.Default.DarkMode,
                    onClick = { onThemeSelected(AccountbookPreferences.Theme.DARK) },
                    isSelected = selected == AccountbookPreferences.Theme.DARK,
                )
            }
        },
        modifier = modifier,
    )
}

@Composable
private fun ThemeButton(
    isSelected: Boolean,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    FilledIconToggleButton(
        checked = isSelected,
        onCheckedChange = { onClick() },
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
        )
    }
}


inline fun LazyListScope.itemSpacer(height: Dp) {
    item {
        Spacer(
            Modifier
                .height(height)
                .fillParentMaxWidth(),
        )
    }
}