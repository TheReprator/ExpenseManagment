package dev.reprator.accountbook.di

import dev.reprator.accountbook.utility.base.ApplicationInfo
import io.ktor.client.engine.*
import io.ktor.client.engine.java.*
import java.io.File
import java.util.prefs.Preferences
import me.tatarka.inject.annotations.Provides

actual interface SharedPlatformApplicationComponent {

    @ApplicationScope
    @Provides
    fun provideHttpClientEngine(): HttpClientEngine = Java.create()
    
    @ApplicationScope
        @Provides
        fun provideApplicationId(): ApplicationInfo = ApplicationInfo(
            packageName = "dev.reprator.accountbook",
            debugBuild = true,
            versionName = "1.0.0",
            versionCode = 1,
            cachePath = { getCacheDir().absolutePath },
        )
    
        @ApplicationScope
        @Provides
        fun providePreferences(): Preferences = Preferences.userRoot().node("dev.reprator.accountbook")
}

private fun getCacheDir(): File = when (currentOperatingSystem) {
    OperatingSystem.Windows -> File(System.getenv("AppData"), "tivi/cache")
    OperatingSystem.Linux -> File(System.getProperty("user.home"), ".cache/tivi")
    OperatingSystem.MacOS -> File(System.getProperty("user.home"), "Library/Caches/tivi")
    else -> throw IllegalStateException("Unsupported operating system")
}

internal enum class OperatingSystem {
    Windows,
    Linux,
    MacOS,
    Unknown,
}

private val currentOperatingSystem: OperatingSystem
    get() {
        val os = System.getProperty("os.name").lowercase()
        return when {
            os.contains("win") -> OperatingSystem.Windows
            os.contains("nix") || os.contains("nux") || os.contains("aix") -> {
                OperatingSystem.Linux
            }

            os.contains("mac") -> OperatingSystem.MacOS
            else -> OperatingSystem.Unknown
        }
    }