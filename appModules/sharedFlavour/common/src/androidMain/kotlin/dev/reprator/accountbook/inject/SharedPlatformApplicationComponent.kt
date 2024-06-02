package dev.reprator.accountbook.inject

import android.app.Application
import android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE
import dev.reprator.core.app.ApplicationInfo
import dev.reprator.core.app.Flavor
import dev.reprator.core.inject.ApplicationScope
import dev.reprator.core.util.AppCoroutineDispatchers
import kotlinx.coroutines.Dispatchers
import me.tatarka.inject.annotations.Provides

actual interface SharedPlatformApplicationComponent {

    @ApplicationScope
    @Provides
    fun provideApplicationInfo(
        application: Application,
        flavor: Flavor,
    ): ApplicationInfo {
        val packageManager = application.packageManager
        val applicationInfo = packageManager.getApplicationInfo(application.packageName, 0)
        val packageInfo = packageManager.getPackageInfo(application.packageName, 0)

        return ApplicationInfo(
            packageName = application.packageName,
            debugBuild = (applicationInfo.flags and FLAG_DEBUGGABLE) != 0,
            flavor = flavor,
            versionName = packageInfo.versionName,
            versionCode = @Suppress("DEPRECATION") packageInfo.versionCode,
            cachePath = { application.cacheDir.absolutePath },
        )
    }

    @ApplicationScope
    @Provides
    fun provideCoroutineDispatchers(): AppCoroutineDispatchers = AppCoroutineDispatchers(
        io = Dispatchers.IO,
        databaseWrite = Dispatchers.IO.limitedParallelism(1),
        databaseRead = Dispatchers.IO.limitedParallelism(4),
        computation = Dispatchers.Default,
        main = Dispatchers.Main,
    )
}
