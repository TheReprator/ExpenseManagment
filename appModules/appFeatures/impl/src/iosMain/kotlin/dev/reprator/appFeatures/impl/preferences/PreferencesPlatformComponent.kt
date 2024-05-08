package dev.reprator.appFeatures.impl.preferences

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.ObservableSettings
import dev.reprator.core.inject.ApplicationScope
import me.tatarka.inject.annotations.Provides
import platform.Foundation.NSUserDefaults

actual interface PreferencesPlatformComponent {
    @ApplicationScope
    @Provides
    fun provideSettings(delegate: NSUserDefaults): ObservableSettings = NSUserDefaultsSettings(delegate)
}
