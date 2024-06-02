package dev.reprator.appFeatures.impl.logger

import dev.reprator.appFeatures.api.logger.Logger

internal fun CompositeLogger(
    vararg loggers: Logger?,
): CompositeLogger = CompositeLogger(loggers = loggers.filterNotNull())

internal class CompositeLogger(
    private val loggers: Collection<Logger>,
) : Logger {
    override fun setUserId(id: String) {
        loggers.forEach { it.setUserId(id) }
    }

    override fun v(throwable: Throwable?, message: () -> String) {
        loggers.forEach { it.v(throwable, message) }
    }

    override fun d(throwable: Throwable?, message: () -> String) {
        loggers.forEach { it.d(throwable, message) }
    }

    override fun i(throwable: Throwable?, message: () -> String) {
        loggers.forEach { it.i(throwable, message) }
    }

    override fun e(throwable: Throwable?, message: () -> String) {
        loggers.forEach { it.e(throwable, message) }
    }

    override fun w(throwable: Throwable?, message: () -> String) {
        loggers.forEach { it.w(throwable, message) }
    }
}
