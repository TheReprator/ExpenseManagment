package dev.reprator.appFeatures.impl.utility

import dev.reprator.appFeatures.api.utility.NetworkListener
import kotlinx.browser.window
import me.tatarka.inject.annotations.Inject
import org.w3c.dom.events.Event

private const val APP_EVENT_OFFLINE = "offline"
private const val APP_EVENT_ONLINE = "online"

@Inject
class WebInternetCheckerImpl : NetworkListener {

    lateinit var callback: (event: Event) -> Unit

    private fun getCurrentNetworkState() = when {
        window.navigator.onLine -> true
        else -> false
    }

    override fun registerListener(onNetworkAvailable: () -> Unit, onNetworkLost: () -> Unit) {

        if (!::callback.isInitialized)
            callback = { _ ->
                val networkState = getCurrentNetworkState()
                
                if (networkState)
                    onNetworkAvailable()
                else
                    onNetworkLost()
            }

        window.addEventListener(APP_EVENT_ONLINE, callback)
        window.addEventListener(APP_EVENT_OFFLINE, callback)

        window.dispatchEvent(Event(APP_EVENT_ONLINE))
    }

    override fun unregisterListener() {
        if (!::callback.isInitialized)
            return

        window.removeEventListener(APP_EVENT_ONLINE, callback)
        window.removeEventListener(APP_EVENT_OFFLINE, callback)
    }
}