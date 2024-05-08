package dev.reprator.appFeatures.impl.logger

import dev.reprator.appFeatures.api.logger.RecordingLogger
import dev.reprator.core.app.ApplicationInfo
import dev.reprator.core.app.Flavor
import dev.reprator.core.appinitializers.AppInitializer
import dev.reprator.core.inject.ApplicationScope
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

expect interface LoggerPlatformComponent

interface LoggerComponent : LoggerPlatformComponent {
    @ApplicationScope
    @Provides
    fun bindRecordingLogger(
        applicationInfo: ApplicationInfo,
    ): RecordingLogger = when {
        applicationInfo.debugBuild || (applicationInfo.flavor == Flavor.Qa) -> RecordingLoggerImpl()
        else -> NoopRecordingLogger
    }

    @Provides
    @IntoSet
    fun provideCrashReportingInitializer(impl: CrashReportingInitializer): AppInitializer = impl
}
