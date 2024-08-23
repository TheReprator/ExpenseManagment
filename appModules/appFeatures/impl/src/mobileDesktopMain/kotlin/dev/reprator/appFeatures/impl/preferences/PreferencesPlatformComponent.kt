package dev.reprator.appFeatures.impl.preferences

import dev.reprator.core.app.ApplicationInfo
import dev.reprator.core.inject.ApplicationScope
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import me.tatarka.inject.annotations.Provides
import okio.Path.Companion.toPath

actual interface PreferencesPlatformComponent {

    @ApplicationScope
    @Provides
    fun provideSettings(applicationInfo: ApplicationInfo): Lazy<KStore<AccountBookSetting>> {
        return lazy {
            storeOf(
                file = "${applicationInfo.cachePath()}/settings.json".toPath(),
                default = AccountBookSetting()
            )
        }
    }

}
