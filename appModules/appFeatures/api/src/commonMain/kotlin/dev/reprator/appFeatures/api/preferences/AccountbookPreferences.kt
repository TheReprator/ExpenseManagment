package dev.reprator.appFeatures.api.preferences

import kotlinx.coroutines.flow.Flow

interface AccountbookPreferences {
    suspend fun setTheme(theme: Theme)
    fun observeTheme(): Flow<Theme>

    suspend fun toggleUseDynamicColors()

    fun observeUseDynamicColors(): Flow<Boolean>

    suspend fun getUseLessData(): Boolean

    suspend fun toggleUseLessData()

    fun observeUseLessData(): Flow<Boolean>

    suspend fun toggleReportAppCrashes()
    fun observeReportAppCrashes(): Flow<Boolean>

    suspend fun toggleReportAnalytics()
    fun observeReportAnalytics(): Flow<Boolean>

    enum class Theme {
        LIGHT,
        DARK,
        SYSTEM,
    }
}
