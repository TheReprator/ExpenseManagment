package dev.reprator.accountbook.di

import dev.reprator.accountbook.utility.base.ApplicationInfo
import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*
import me.tatarka.inject.annotations.Provides
import kotlin.experimental.ExperimentalNativeApi
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSBundle
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDefaults
import platform.Foundation.NSUserDomainMask

actual interface SharedPlatformApplicationComponent {

    @ApplicationScope
    @Provides
    fun provideHttpClientEngine(): HttpClientEngine = Darwin.create()
    
    @Provides
    fun provideNsUserDefaults(): NSUserDefaults = NSUserDefaults.standardUserDefaults

    @OptIn(ExperimentalNativeApi::class)
    @ApplicationScope
    @Provides
    fun provideApplicationId(): ApplicationInfo = ApplicationInfo(
        packageName = NSBundle.mainBundle.bundleIdentifier ?: error("Bundle ID not found"),
        debugBuild = Platform.isDebugBinary,
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