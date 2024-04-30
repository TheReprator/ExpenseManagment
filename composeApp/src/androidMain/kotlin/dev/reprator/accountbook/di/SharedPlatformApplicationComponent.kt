package dev.reprator.accountbook.di

import android.app.Application
import android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE
import dev.reprator.accountbook.utility.base.ApplicationInfo
import io.ktor.client.engine.*
import io.ktor.client.engine.android.*
import me.tatarka.inject.annotations.Provides

actual interface SharedPlatformApplicationComponent {

    @ApplicationScope
    @Provides
    fun provideHttpClientEngine(): HttpClientEngine = Android.create()
    
    @ApplicationScope
    @Provides
    fun provideApplicationInfo(
        application: Application,
    ): ApplicationInfo {
        val packageManager = application.packageManager
        val applicationInfo = packageManager.getApplicationInfo(application.packageName, 0)
        val packageInfo = packageManager.getPackageInfo(application.packageName, 0)

        return ApplicationInfo(
            packageName = application.packageName,
            debugBuild = (applicationInfo.flags and FLAG_DEBUGGABLE) != 0,
            versionName = packageInfo.versionName,
            versionCode = @Suppress("DEPRECATION") packageInfo.versionCode,
            cachePath = { application.cacheDir.absolutePath },
        )
    }
}