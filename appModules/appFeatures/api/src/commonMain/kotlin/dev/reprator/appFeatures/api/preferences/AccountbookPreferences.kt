package dev.reprator.appFeatures.api.preferences

import kotlinx.coroutines.flow.Flow

interface AccountbookPreferences {
    suspend fun setTheme(theme: Theme)
    suspend fun observeTheme(): Flow<Theme>

    suspend fun toggleUseDynamicColors()

    suspend fun observeUseDynamicColors(): Flow<Boolean>

    suspend fun getUseLessData(): Boolean

    suspend fun toggleUseLessData()

    suspend fun observeUseLessData(): Flow<Boolean>

    suspend fun toggleReportAppCrashes()
    suspend fun observeReportAppCrashes(): Flow<Boolean>

    suspend fun toggleReportAnalytics()
    suspend fun observeReportAnalytics(): Flow<Boolean>

    enum class Theme {
        LIGHT,
        DARK,
        SYSTEM,
    }
}