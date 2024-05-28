package dev.reprator.appFeatures.impl.utility

import dev.reprator.appFeatures.api.utility.InternetChecker
import kotlinx.browser.window
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import me.tatarka.inject.annotations.Inject
import org.w3c.dom.events.Event

@Inject
class WebInternetCheckerImpl() : NetworkListener {

    override val isInternetAvailableFlow = callbackFlow {
        
        val currentNetworkState = getCurrentNetworkState()
        trySend(currentNetworkState)

        val callback: (event: Event) -> Unit = { _ ->
            val networkState = getCurrentNetworkState()
            trySend(networkState)
        }

        window.addEventListener("online", callback)
        window.addEventListener("offline", callback)

        awaitClose {
            window.removeEventListener("offline", callback)
            window.removeEventListener("online", callback)
        }
    }

    override suspend fun startObserving() {
        isInternetAvailableFlow.collect {
            isInternetAvailable = it
        }
    }

    override fun stopObserving() {
    }

    private fun getCurrentNetworkState() = when {
        window.navigator.onLine -> true
        else -> false
    }
}