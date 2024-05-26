package dev.reprator.appFeatures.impl.utility

import dev.reprator.appFeatures.api.utility.InternetChecker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import me.tatarka.inject.annotations.Inject
import java.io.IOException
import java.net.InetAddress
import java.net.Socket

@Inject
class DesktopInternetCheckerImpl : InternetChecker {

    override val isInternetAvailable: Flow<Boolean>
        get() {
            return isInternetConnected()
        }

    private fun isInternetConnected() = callbackFlow {
        try {
            // Create a socket to the google DNS server
            val socket = Socket(InetAddress.getByName("8.8.8.8"), 53)

            // If the socket is connected, the internet is available
            val result = if (socket.isConnected) {
                println("Internet is available")
                true
            } else {
                println("Internet is not available")
                false
            }

            // Close the socket
            socket.close()
            trySend(true)
        } catch (e: IOException) {
            println("Error checking internet connectivity: " + e.message)
            trySend(false)
        }

        awaitClose {

        }
    }
        .flowOn(Dispatchers.IO)
}