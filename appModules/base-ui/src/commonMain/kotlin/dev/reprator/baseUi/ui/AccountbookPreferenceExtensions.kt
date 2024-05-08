package dev.reprator.baseUi.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import dev.reprator.appFeatures.api.preferences.AccountbookPreferences

@Composable
fun AccountbookPreferences.shouldUseDarkColors(): Boolean {
    val themePreference = remember { observeTheme() }
        .collectAsState(initial = AccountbookPreferences.Theme.SYSTEM)

    return when (themePreference.value) {
        AccountbookPreferences.Theme.LIGHT -> false
        AccountbookPreferences.Theme.DARK -> true
        else -> isSystemInDarkTheme()
    }
}

@Composable
fun AccountbookPreferences.shouldUseDynamicColors(): Boolean {
    return remember { observeUseDynamicColors() }
        .collectAsState(initial = true)
        .value
}
