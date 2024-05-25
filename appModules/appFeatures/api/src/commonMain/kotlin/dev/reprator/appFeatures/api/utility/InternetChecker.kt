package dev.reprator.appFeatures.api.utility

interface InternetChecker {
    val isInternetAvailable: Boolean
    
    fun startObserving()
    fun stopObserving()
}