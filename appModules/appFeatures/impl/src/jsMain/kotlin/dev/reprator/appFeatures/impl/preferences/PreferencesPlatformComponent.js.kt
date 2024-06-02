package dev.reprator.appFeatures.impl.preferences

import dev.reprator.core.inject.ApplicationScope
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.storage.storeOf
import me.tatarka.inject.annotations.Provides

actual interface PreferencesPlatformComponent {

    @ApplicationScope
    @Provides
    fun provideSettings(): Lazy<KStore<AccountBookSetting>> {

        return lazy {
            storeOf(
                key = "settings.json",
                default = AccountBookSetting()
            )
        }
    }

}
