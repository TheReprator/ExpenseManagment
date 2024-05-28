package dev.reprator.appFeatures.api.logger

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

import kotlin.native.HiddenFromObjC

@HiddenFromObjC
interface RecordingLogger : Logger {
    val buffer: Flow<List<LogMessage>>
}


@HiddenFromObjC
enum class Severity {
    Verbose,
    Debug,
    Info,
    Warn,
    Error,
    Assert,
}

@HiddenFromObjC
data class LogMessage(
    val severity: Severity,
    val message: String,
    val throwable: Throwable?,
    val timestamp: Instant = Clock.System.now(),
)
