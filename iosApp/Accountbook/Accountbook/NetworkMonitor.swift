//
//  NetworkMonitor.swift
//  Accountbook
//
//  Created by Vikram Singh on 24/05/2024.
//

import Foundation
import Network
import AccounBookKt

final class NetworkMonitor: NetworkListener {
    
    private let monitor: NWPathMonitor
    
    init() {
        monitor = NWPathMonitor()
    }
    
    func registerListener(onNetworkAvailable: @escaping () -> Void, onNetworkLost: @escaping () -> Void) {
        monitor.pathUpdateHandler = { path in
            if path.status == .satisfied {
                onNetworkAvailable()
            } else {
                onNetworkLost()
            }
        }
        monitor.start(queue: DispatchQueue.global(qos: .background))
    }
    
    func unregisterListener() {
        monitor.cancel()
    }

}

