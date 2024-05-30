package dev.reprator.accountbook.inject

import dev.reprator.core.app.Flavor
import dev.reprator.core.inject.ApplicationScope
import kotlin.experimental.ExperimentalNativeApi
import kotlinx.cinterop.ExperimentalForeignApi
import me.tatarka.inject.annotations.Provides
import platform.Foundation.NSBundle
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDefaults
import platform.Foundation.NSUserDomainMask
import dev.reprator.core.app.ApplicationInfo
import dev.reprator.core.util.AppCoroutineDispatchers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO

actual interface SharedPlatformApplicationComponent {

    @OptIn(ExperimentalCoroutinesApi::class)
    @ApplicationScope
    @Provides
    fun provideCoroutineDispatchers(): AppCoroutineDispatchers = AppCoroutineDispatchers(
        io = Dispatchers.IO,
        databaseWrite = Dispatchers.IO.limitedParallelism(1),
        databaseRead = Dispatchers.IO.limitedParallelism(4),
        computation = Dispatchers.Default,
        main = Dispatchers.Main,
    )

    @Provides
    fun provideNsUserDefaults(): NSUserDefaults = NSUserDefaults.standardUserDefaults

    @OptIn(ExperimentalNativeApi::class)
    @ApplicationScope
    @Provides
    fun provideApplicationId(
        flavor: Flavor,
    ): ApplicationInfo = ApplicationInfo(
        packageName = NSBundle.mainBundle.bundleIdentifier ?: error("Bundle ID not found"),
        debugBuild = Platform.isDebugBinary,
        flavor = flavor,
        versionName = NSBundle.mainBundle.infoDictionary
            ?.get("CFBundleShortVersionString") as? String
            ?: "",
        versionCode = (NSBundle.mainBundle.infoDictionary?.get("CFBundleVersion") as? String)
            ?.toIntOrNull()
            ?: 0,
        cachePath = { NSFileManager.defaultManager.cacheDir },
    )
}

@OptIn(ExperimentalForeignApi::class)
private val NSFileManager.cacheDir: String
    get() = URLForDirectory(
        directory = NSCachesDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = true,
        error = null,
    )?.path.orEmpty()
