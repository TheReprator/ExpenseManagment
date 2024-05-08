package dev.reprator.appFeatures.impl.logger

import co.touchlab.crashkios.crashlytics.enableCrashlytics
import co.touchlab.crashkios.crashlytics.setCrashlyticsUnhandledExceptionHook
import dev.reprator.core.appinitializers.AppInitializer

internal object IosCrashKiOSInitializer : AppInitializer {
    override fun initialize() {
        // https://crashkios.touchlab.co/docs/crashlytics#step-2---add-crashkios
        enableCrashlytics()
        setCrashlyticsUnhandledExceptionHook()
    }
}
