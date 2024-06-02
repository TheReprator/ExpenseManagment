package dev.reprator.appFeatures.impl.logger

import co.touchlab.crashkios.crashlytics.CrashlyticsKotlin
import dev.reprator.appFeatures.api.logger.Logger

internal object CrashKIosLogger : Logger {

    override fun setUserId(id: String) {
        CrashlyticsKotlin.setCustomValue("username", id)
    }

    override fun i(throwable: Throwable?, message: () -> String) {
        sendToCrashlytics(throwable, message)
    }

    override fun e(throwable: Throwable?, message: () -> String) {
        sendToCrashlytics(throwable, message)
    }

    override fun w(throwable: Throwable?, message: () -> String) {
        sendToCrashlytics(throwable, message)
    }

    private inline fun sendToCrashlytics(throwable: Throwable?, message: () -> String) {
        CrashlyticsKotlin.logMessage(message())
        if (throwable != null) {
            CrashlyticsKotlin.sendHandledException(throwable)
        }
    }
}
