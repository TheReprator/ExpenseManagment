package dev.reprator.baseUi.behaviour

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import dev.reprator.appFeatures.api.preferences.AccountbookPreferences

@Composable
fun AccountbookPreferences.shouldUseDarkColors(): Boolean {

    val themePreference by produceState(initialValue = AccountbookPreferences.Theme.SYSTEM) {
             observeTheme().collect{
                 value = it
            }
        }

    return when (themePreference) {
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
