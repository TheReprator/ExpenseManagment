package dev.reprator.baseUi.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import dev.reprator.appFeatures.api.preferences.AccountbookPreferences

@Composable
fun AccountbookPreferences.shouldUseDarkColors(): Boolean {

    val themePreference =
        produceState<AccountbookPreferences.Theme>(initialValue = AccountbookPreferences.Theme.SYSTEM) {
            observeTheme()
        }

    return when (themePreference.value) {
        AccountbookPreferences.Theme.LIGHT -> false
        AccountbookPreferences.Theme.DARK -> true
        else -> isSystemInDarkTheme()
    }
}

@Composable
fun AccountbookPreferences.shouldUseDynamicColors(): Boolean {
    return produceState(initialValue = true) {
        observeUseDynamicColors()
    }.value
}
