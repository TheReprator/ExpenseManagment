package dev.reprator.accountbook

import android.app.Application
import android.os.Build
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy
import androidx.lifecycle.ProcessLifecycleOwner
import dev.reprator.accountbook.inject.AndroidApplicationComponent
import dev.reprator.accountbook.inject.create

class AccountbookApplication : Application() {

    val component: AndroidApplicationComponent by lazy {
        AndroidApplicationComponent.create(this)
    }

    override fun onCreate() {
        super.onCreate()

        setupStrictMode()

        component.initializers.initialize()

        ProcessLifecycleOwner.get().lifecycle
            .addObserver(ApplicaitonLifeCycleObserver(component.applicationLifeCycle))
    }
}

private fun setupStrictMode() {
    StrictMode.setThreadPolicy(
        ThreadPolicy.Builder()
            .detectAll()
            .penaltyLog()
            .build(),
    )
    StrictMode.setVmPolicy(
        VmPolicy.Builder()
            .detectLeakedSqlLiteObjects()
            .detectActivityLeaks()
            .detectLeakedClosableObjects()
            .detectLeakedRegistrationObjects()
            .detectFileUriExposure()
            .detectCleartextNetwork()
            .apply {
                if (Build.VERSION.SDK_INT >= 26) {
                    detectContentUriWithoutPermission()
                }
                if (Build.VERSION.SDK_INT >= 29) {
                    detectCredentialProtectedWhileLocked()
                }
                if (Build.VERSION.SDK_INT >= 31) {
                    detectIncorrectContextUse()
                    detectUnsafeIntentLaunch()
                }
            }
            .penaltyLog()
            .build(),
    )
}
