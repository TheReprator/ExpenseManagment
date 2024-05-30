package dev.reprator.accountbook.inject

import dev.reprator.core.app.ApplicationInfo
import dev.reprator.core.app.Flavor
import dev.reprator.core.inject.ApplicationScope
import dev.reprator.core.util.AppCoroutineDispatchers
import me.tatarka.inject.annotations.Provides
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

actual interface SharedPlatformApplicationComponent {

    @ApplicationScope
    @Provides
    fun provideCoroutineDispatchers(): AppCoroutineDispatchers = AppCoroutineDispatchers(
        io = Dispatchers.Default,
        databaseWrite = Dispatchers.Default,
        databaseRead = Dispatchers.Default,
        computation = Dispatchers.Default,
        main = Dispatchers.Main,
    )

    @ApplicationScope
    @Provides
    fun provideApplicationId(
        flavor: Flavor,
    ): ApplicationInfo = ApplicationInfo(
        packageName = "dev.reprator.accountbook",
        debugBuild = true,
        flavor = flavor,
        versionName = "1.0.0",
        versionCode = 1,
        cachePath = { "" },
    )
}