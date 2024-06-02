package dev.reprator.appFeatures.impl.utility

import android.app.Application
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import dev.reprator.appFeatures.api.utility.NetworkListener
import me.tatarka.inject.annotations.Inject

@Inject
class AndroidNetworkListenerImpl(private val context: Application) : NetworkListener {

    private var networkCallback: ConnectivityManager.NetworkCallback? = null
    private val connectivityManager by lazy { context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager }

    override fun registerListener(onNetworkAvailable: () -> Unit, onNetworkLost: () -> Unit) {
        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                onNetworkAvailable()
            }

            override fun onUnavailable() {
                onNetworkLost()
            }

            override fun onLost(network: Network) {
                onNetworkLost()
            }
        }
        networkCallback?.let { connectivityManager.registerDefaultNetworkCallback(it) }
    }

    override fun unregisterListener() {
        networkCallback?.let { connectivityManager.unregisterNetworkCallback(it) }
    }
}