//
//  NetworkMonitor.swift
//  Accountbook
//
//  Created by Vikram Singh on 24/05/2024.
//

import Foundation
import Network
import AccounBookKt

extension NWInterface.InterfaceType: CaseIterable {
    public static var allCases: [NWInterface.InterfaceType] = [
        .other,
        .wifi,
        .cellular,
        .loopback,
        .wiredEthernet
    ]
}

final class NetworkMonitor: InternetChecker {

    private let queue = DispatchQueue(label: "NetworkConnectivityMonitor")
    private let monitor: NWPathMonitor

    var isInternetAvailable = false
    private(set) var isExpensive = false
    
    private(set) var currentConnectionType: NWInterface.InterfaceType?

    init() {
        monitor = NWPathMonitor()
    }

    func startObserving() {
        monitor.pathUpdateHandler = { [weak self] path in
            self?.isInternetAvailable = path.status != .unsatisfied
            self?.isExpensive = path.isExpensive
            self?.currentConnectionType = NWInterface.InterfaceType.allCases.filter { path.usesInterfaceType($0) }.first
        }
        monitor.start(queue: queue)
    }

    func stopObserving() {
        monitor.cancel()
    }
}
