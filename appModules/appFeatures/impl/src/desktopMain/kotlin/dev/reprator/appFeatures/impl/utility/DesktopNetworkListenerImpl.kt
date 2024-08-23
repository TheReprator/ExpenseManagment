package dev.reprator.appFeatures.impl.utility

import dev.reprator.appFeatures.api.utility.NetworkListener
import me.tatarka.inject.annotations.Inject
import java.io.IOException
import java.net.InetAddress
import java.net.Socket

@Inject
class DesktopNetworkListenerImpl : NetworkListener {

    override fun registerListener(onNetworkAvailable: () -> Unit, onNetworkLost: () -> Unit) {
        try {
            // Create a socket to the google DNS server
            val socket = Socket(InetAddress.getByName("8.8.8.8"), 53)

            // If the socket is connected, the internet is available
            if (socket.isConnected) {
                onNetworkAvailable()
            } else {
                onNetworkLost()
            }

            // Close the socket
            socket.close()
        } catch (e: IOException) {
            onNetworkLost()
        }
    }

    override fun unregisterListener() {
    }
}