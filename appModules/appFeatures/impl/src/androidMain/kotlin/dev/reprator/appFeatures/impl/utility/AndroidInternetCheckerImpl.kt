package dev.reprator.appFeatures.impl.utility

import android.app.Application
import dev.reprator.appFeatures.api.utility.InternetChecker
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import me.tatarka.inject.annotations.Inject

@Inject
class AndroidInternetCheckerImpl(private val context: Application) : InternetChecker {

    override val isInternetAvailable: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val connectivityManager: ConnectivityManager by lazy {
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .also { builder ->
            builder.addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        }
        .build()

    private val availableNetworks = mutableSetOf<Network>()

//     override val isInternetAvailable = callbackFlow {
//
//        trySend(connectivityManager.getCurrentNetworkState())

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)

                availableNetworks.add(network)

                val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

                isInternetAvailable.value = networkCapabilities?.asNetworkState() ?: false
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)

                isInternetAvailable.value = networkCapabilities.asNetworkState()
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                availableNetworks.remove(network)

                if (availableNetworks.isEmpty()) {
                    isInternetAvailable.value = false
                }
            }
        }

//        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
//        awaitClose {
//            connectivityManager.unregisterNetworkCallback(networkCallback)
//        }



    fun ConnectivityManager.getCurrentNetworkState(): Boolean {
        val networkCapabilities = getNetworkCapabilities(activeNetwork)

        return networkCapabilities?.asNetworkState() ?: false
    }

    private fun NetworkCapabilities.asNetworkState(): Boolean {
        val connected = hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        val metered = !hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)

        return when {
            connected -> metered
            else -> false
        }
    }
}