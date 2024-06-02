package dev.reprator.appFeatures.impl.logger

import co.touchlab.crashkios.crashlytics.enableCrashlytics
import dev.reprator.core.appinitializers.AppInitializer

internal object AndroidCrashKiOSInitializer : AppInitializer {
    override fun initialize() {
        enableCrashlytics()
    }
}
