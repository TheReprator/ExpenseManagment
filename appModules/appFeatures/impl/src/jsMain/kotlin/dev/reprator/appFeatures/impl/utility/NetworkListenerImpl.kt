package dev.reprator.appFeatures.impl.utility

import dev.reprator.appFeatures.api.utility.NetworkListener
import kotlinx.browser.window
import me.tatarka.inject.annotations.Inject
import org.w3c.dom.events.Event

@Inject
class WebInternetCheckerImpl : NetworkListener {

    lateinit var callback: (event: Event) -> Unit

    private fun getCurrentNetworkState() = when {
        window.navigator.onLine -> true
        else -> false
    }

    override fun registerListener(onNetworkAvailable: () -> Unit, onNetworkLost: () -> Unit) {

        callback = { _ ->
            val networkState = getCurrentNetworkState()
            if (networkState)
                onNetworkAvailable()
            else
                onNetworkLost()
        }

        window.addEventListener("online", callback)
        window.addEventListener("offline", callback)
    }

    override fun unregisterListener() {
        if(!::callback.isInitialized)
            return
        
        window.removeEventListener("offline", callback)
        window.removeEventListener("online", callback)
    }
}