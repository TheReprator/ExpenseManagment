package dev.reprator.appFeatures.impl.logger

import dev.reprator.appFeatures.api.logger.Logger
import dev.reprator.appFeatures.api.logger.RecordingLogger
import dev.reprator.core.inject.ApplicationScope
import me.tatarka.inject.annotations.Provides

actual interface LoggerPlatformComponent {
    @Provides
    @ApplicationScope
    fun provideLogger(
        kermitLogger: KermitLogger,
        recordingLogger: RecordingLogger,
    ): Logger = CompositeLogger(kermitLogger, recordingLogger)

    @Provides
    fun bindSetCrashReportingEnabledAction(): SetCrashReportingEnabledAction {
        return NoopSetCrashReportingEnabledAction
    }
}

private object NoopSetCrashReportingEnabledAction : SetCrashReportingEnabledAction {
    override fun invoke(enabled: Boolean) {}
}
