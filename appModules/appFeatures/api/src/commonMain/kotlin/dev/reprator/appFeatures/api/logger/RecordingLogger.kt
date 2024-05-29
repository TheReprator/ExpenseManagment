package dev.reprator.appFeatures.api.logger

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.experimental.ExperimentalObjCRefinement

import kotlin.native.HiddenFromObjC

@OptIn(ExperimentalObjCRefinement::class)
@HiddenFromObjC
interface RecordingLogger : Logger {
    val buffer: Flow<List<LogMessage>>
}


@OptIn(ExperimentalObjCRefinement::class)
@HiddenFromObjC
enum class Severity {
    Verbose,
    Debug,
    Info,
    Warn,
    Error,
    Assert,
}

@OptIn(ExperimentalObjCRefinement::class)
@HiddenFromObjC
data class LogMessage(
    val severity: Severity,
    val message: String,
    val throwable: Throwable?,
    val timestamp: Instant = Clock.System.now(),
)
