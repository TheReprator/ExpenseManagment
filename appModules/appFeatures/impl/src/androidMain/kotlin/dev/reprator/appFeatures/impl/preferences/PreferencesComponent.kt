@file:Suppress("DEPRECATION")

package dev.reprator.appFeatures.impl.preferences

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.SharedPreferencesSettings
import dev.reprator.core.inject.ApplicationScope
import me.tatarka.inject.annotations.Provides

actual interface PreferencesPlatformComponent {
    @ApplicationScope
    @Provides
    fun provideSettings(delegate: AppSharedPreferences): ObservableSettings {
        return SharedPreferencesSettings(delegate)
    }

    @ApplicationScope
    @Provides
    fun provideAppPreferences(
        context: Application,
    ): AppSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
}

typealias AppSharedPreferences = SharedPreferences
