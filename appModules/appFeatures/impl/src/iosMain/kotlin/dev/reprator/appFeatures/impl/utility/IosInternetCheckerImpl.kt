package dev.reprator.appFeatures.impl.utility

import cocoapods.Reachability.NetworkStatus
import cocoapods.Reachability.Reachability
import cocoapods.Reachability.ReachableViaWWAN
import cocoapods.Reachability.ReachableViaWiFi
import dev.reprator.appFeatures.api.utility.InternetChecker
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import me.tatarka.inject.annotations.Inject

@Inject
@ExperimentalForeignApi
class IosInternetCheckerImpl(override val isInternetAvailable: Boolean = false) : InternetChecker {

    private val reachability: Reachability? by lazy {
        Reachability.reachabilityForInternetConnection()
    }

    val networkState = callbackFlow {
        val currentNetworkState = reachability?.currentReachabilityStatus()?.asNetworkState()
        trySend(currentNetworkState)

        reachability?.reachableBlock = { r ->
            val networkState = r?.currentReachabilityStatus()?.asNetworkState()
            trySend(networkState)
        }

        reachability?.unreachableBlock = { _ ->
            trySend(false)
        }

        reachability?.startNotifier()
        awaitClose {
            reachability?.stopNotifier()
        }
    }

    private fun NetworkStatus.asNetworkState() = when (this) {
        ReachableViaWWAN, ReachableViaWiFi -> true
        else -> false
    }

    override fun startObserving() {
    }

    override fun stopObserving() {
    }
}