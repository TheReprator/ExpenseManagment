package dev.reprator.accountbook

import dev.reprator.commonFeatureImpl.setupServerPlugin
import io.ktor.server.application.Application


fun Application.module() {
    configureServerMonitoring()
    setupServerPlugin()
}