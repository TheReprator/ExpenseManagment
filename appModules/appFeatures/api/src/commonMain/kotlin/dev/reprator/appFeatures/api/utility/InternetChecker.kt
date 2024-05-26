package dev.reprator.appFeatures.api.utility

import kotlinx.coroutines.flow.StateFlow

interface InternetChecker : ApplicationLifeCycle {
    val isInternetAvailable: StateFlow<Boolean>
}