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
                println("vikram::DesktopNetworkListenerImpl:: Internet is available")
                onNetworkAvailable()
            } else {
                println("vikram::DesktopNetworkListenerImpl:: Internet is not available")
                onNetworkLost()
            }

            // Close the socket
            socket.close()
        } catch (e: IOException) {
            println("vikram::DesktopNetworkListenerImpl:: Error checking internet connectivity: " + e.message)
            onNetworkLost()
        }
    }

    override fun unregisterListener() {
        println("vikram::DesktopNetworkListenerImpl:: unregisterListener")
    }
}