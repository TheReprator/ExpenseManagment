package dev.reprator.appFeatures.impl.preferences

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.PreferencesSettings
import dev.reprator.core.inject.ApplicationScope
import java.util.prefs.Preferences
import me.tatarka.inject.annotations.Provides

actual interface PreferencesPlatformComponent {
    @ApplicationScope
    @Provides
    fun provideSettings(delegate: Preferences): ObservableSettings = PreferencesSettings(delegate)
}
