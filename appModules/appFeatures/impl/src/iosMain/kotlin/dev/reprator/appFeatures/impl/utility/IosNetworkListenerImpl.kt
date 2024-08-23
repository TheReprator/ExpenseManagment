package dev.reprator.appFeatures.impl.utility

import dev.reprator.appFeatures.api.utility.NetworkListener
import me.tatarka.inject.annotations.Inject
import platform.Network.nw_interface_type_wifi
import platform.Network.nw_path_monitor_cancel
import platform.Network.nw_path_monitor_create
import platform.Network.nw_path_monitor_set_queue
import platform.Network.nw_path_monitor_set_update_handler
import platform.Network.nw_path_monitor_start
import platform.Network.nw_path_status_satisfied
import platform.Network.nw_path_uses_interface_type
import platform.NetworkExtension.NWPath
import platform.NetworkExtension.NWPathStatus
import platform.darwin.DISPATCH_QUEUE_SERIAL_WITH_AUTORELEASE_POOL
import platform.darwin.dispatch_queue_create

@Inject
class IosNetworkListenerImpl : NetworkListener {

    private val monitor = nw_path_monitor_create()
    private val queue = dispatch_queue_create(
        label = "dev.reprator.accountbook.connectivity.monitor",
        attr = DISPATCH_QUEUE_SERIAL_WITH_AUTORELEASE_POOL,
    )

    override fun registerListener(onNetworkAvailable: () -> Unit, onNetworkLost: () -> Unit) {
        
        nw_path_monitor_set_update_handler(monitor) { path ->
            val nwPath: NWPath? = path as? NWPath
            val status: NWPathStatus? = nwPath?.status()
            when {
                status != null && status == nw_path_status_satisfied.toLong() -> {
                    val isWifi = nw_path_uses_interface_type(path, nw_interface_type_wifi)
                    val isMetered = !isWifi && (path.isExpensive() || path.isConstrained())

                    if(isMetered)
                        onNetworkAvailable()
                    else
                        onNetworkLost()
                }

                else -> onNetworkLost()
            }
        }

        nw_path_monitor_set_queue(monitor, queue)
        nw_path_monitor_start(monitor)
    }

    override fun unregisterListener() {
        nw_path_monitor_cancel(monitor)
    }

}