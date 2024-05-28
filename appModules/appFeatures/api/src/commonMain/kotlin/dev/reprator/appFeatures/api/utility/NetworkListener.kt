package dev.reprator.appFeatures.api.utility

interface NetworkListener {
    fun registerListener(onNetworkAvailable: () -> Unit, onNetworkLost: () -> Unit)
    fun unregisterListener()
}