//
//  ContentView.swift
//  Accountbook
//
//  Created by Vikram Singh on 09/05/2024.
//

import SwiftUI
import AccounBookKt

struct ContentView: View {
    private let component: HomeUiControllerComponent

    init(component: HomeUiControllerComponent) {
        self.component = component
    }

    var body: some View {
        ComposeView(component: self.component)
            //.ignoresSafeArea(.all, edges: .all)
            .ignoresSafeArea(.keyboard)
    }
}

struct ComposeView: UIViewControllerRepresentable {
    private let component: HomeUiControllerComponent

    init(component: HomeUiControllerComponent) {
        self.component = component
    }

    func makeUIViewController(context _: Context) -> UIViewController {
        return component.uiViewControllerFactory()
    }

    func updateUIViewController(_: UIViewController, context _: Context) {}
}
