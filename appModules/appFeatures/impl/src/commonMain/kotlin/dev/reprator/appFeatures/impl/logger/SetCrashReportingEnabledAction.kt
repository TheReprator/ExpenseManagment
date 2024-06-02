package dev.reprator.appFeatures.impl.logger

fun interface SetCrashReportingEnabledAction {
    operator fun invoke(enabled: Boolean)
}
