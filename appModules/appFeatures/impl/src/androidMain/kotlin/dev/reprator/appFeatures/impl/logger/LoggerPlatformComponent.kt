package dev.reprator.appFeatures.impl.logger

import dev.reprator.appFeatures.api.logger.Logger
import dev.reprator.appFeatures.api.logger.RecordingLogger
import dev.reprator.core.appinitializers.AppInitializer
import dev.reprator.core.inject.ApplicationScope
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

actual interface LoggerPlatformComponent {
    @ApplicationScope
    @Provides
    fun provideLogger(
        timberLogger: TimberLogger,
        recordingLogger: RecordingLogger,
    ): Logger = CompositeLogger(timberLogger, recordingLogger)

    @Provides
    @IntoSet
    fun provideCrashKiOSInitializer(): AppInitializer = AndroidCrashKiOSInitializer

    @Provides
    fun bindSetCrashReportingEnabledAction(): SetCrashReportingEnabledAction {
        return AndroidSetCrashReportingEnabledAction
    }
}
