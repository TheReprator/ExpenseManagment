package dev.reprator.appFeatures.impl.preferences

import dev.reprator.appFeatures.api.preferences.AccountbookPreferences
import dev.reprator.core.inject.ApplicationScope
import me.tatarka.inject.annotations.Provides

expect interface PreferencesPlatformComponent

interface PreferencesComponent : PreferencesPlatformComponent {
    val preferences: AccountbookPreferences

    @ApplicationScope
    @Provides
    fun providePreferences(bind: AccountbookPreferencesImpl): AccountbookPreferences = bind
}
