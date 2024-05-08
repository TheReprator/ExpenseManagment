package dev.reprator.appFeatures.impl.logger

import dev.reprator.appFeatures.api.logger.Logger
import dev.reprator.appFeatures.api.logger.RecordingLogger
import dev.reprator.core.appinitializers.AppInitializer
import dev.reprator.core.inject.ApplicationScope
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

actual interface LoggerPlatformComponent {

    @get:Provides
    val setCrashReportingEnabledAction: SetCrashReportingEnabledAction

    @Provides
    @ApplicationScope
    fun provideLogger(
        kermitLogger: KermitLogger,
        recordingLogger: RecordingLogger,
    ): Logger = CompositeLogger(kermitLogger, recordingLogger, CrashKIosLogger)

    @Provides
    @IntoSet
    fun provideCrashKiOSInitializer(): AppInitializer = IosCrashKiOSInitializer
}
