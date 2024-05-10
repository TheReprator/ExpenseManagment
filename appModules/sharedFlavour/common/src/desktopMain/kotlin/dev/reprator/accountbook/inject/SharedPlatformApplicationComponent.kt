package dev.reprator.accountbook.inject

import dev.reprator.core.app.ApplicationInfo
import dev.reprator.core.app.Flavor
import dev.reprator.core.inject.ApplicationScope
import java.io.File
import java.util.prefs.Preferences
import me.tatarka.inject.annotations.Provides

actual interface SharedPlatformApplicationComponent {
    @ApplicationScope
    @Provides
    fun provideApplicationId(
        flavor: Flavor,
    ): ApplicationInfo = ApplicationInfo(
        packageName = "app.tivi",
        debugBuild = true,
        flavor = flavor,
        versionName = "1.0.0",
        versionCode = 1,
        cachePath = { getCacheDir().absolutePath },
    )

    @ApplicationScope
    @Provides
    fun providePreferences(): Preferences = Preferences.userRoot().node("dev.reprator.accountbook")
}

private fun getCacheDir(): File = when (currentOperatingSystem) {
    OperatingSystem.Windows -> File(System.getenv("AppData"), "accountbook/cache")
    OperatingSystem.Linux -> File(System.getProperty("user.home"), ".cache/accountbook")
    OperatingSystem.MacOS -> File(System.getProperty("user.home"), "Library/Caches/accountbook")
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
