//
//  AccountbookApp.swift
//  Accountbook
//
//  Created by Vikram Singh on 09/05/2024.
//

import FirebaseAnalytics
import FirebaseCore
import FirebaseCrashlytics
import SwiftUI
import AccounBookKt

class AppDelegate: UIResponder, UIApplicationDelegate {
    
    lazy var applicationComponent: IosApplicationComponent = createApplicationComponent(
        appDelegate: self
    )
    
    func application(
        _: UIApplication,
        didFinishLaunchingWithOptions _: [UIApplication.LaunchOptionsKey: Any]? = nil
    ) -> Bool {
        if !(FirebaseOptions.defaultOptions()?.apiKey?.isEmpty ?? true) {
            FirebaseApp.configure()
        }
        applicationComponent.initializers.initialize()
        return true
    }
    
    func application(
        _: UIApplication,
        open url: URL,
        options _: [UIApplication.OpenURLOptionsKey: Any] = [:]
    ) -> Bool {
        return false
    }
    
    func applicationWillTerminate(_ application: UIApplication) {
        // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
        NSLog("VikramIOS:: applicationWillTerminate")
    }
}

@main
struct AccounBookApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
    
    @Environment(\.scenePhase) var scenePhase
    
    var body: some Scene {
        
        let lifeCycle = delegate.applicationComponent.applicationLifeCycle
        
        WindowGroup {
            let uiComponent = createHomeUiControllerComponent(
                applicationComponent: delegate.applicationComponent
            )
            ContentView(component: uiComponent)
        }
        .onChange(of: scenePhase) { (phase) in
            switch phase {
            case .active:
                lifeCycle.isAppInForeGround()
            case .background:
                lifeCycle.isAppInBackground()
            /*case .inactive:
                lifeCycle.isAppInBackground()*/
            @unknown default: print("ScenePhase: unexpected state")
            }
        }
    }
}

private func createApplicationComponent(
    appDelegate: AppDelegate
) -> IosApplicationComponent {
    return IosApplicationComponent.companion.create(
        analytics: FirebaseAnalytics(),
        setInternetChecker: NetworkMonitor(),
        setCrashReportingEnabledAction: IosSetCrashReportingEnabledAction()
    )
}

private class IosSetCrashReportingEnabledAction: ImplSetCrashReportingEnabledAction {
    func invoke(enabled: Bool) {
        Crashlytics.crashlytics().setCrashlyticsCollectionEnabled(enabled)
    }
}

extension UIApplication {
    private class func keyWindowCompat() -> UIWindow? {
        return UIApplication
            .shared
            .connectedScenes
            .flatMap { ($0 as? UIWindowScene)?.windows ?? [] }
            .last { $0.isKeyWindow }
    }
    
    class func topViewController(
        base: UIViewController? = UIApplication.keyWindowCompat()?.rootViewController
    ) -> UIViewController? {
        if let nav = base as? UINavigationController {
            return topViewController(base: nav.visibleViewController)
        }
        
        if let tab = base as? UITabBarController {
            let moreNavigationController = tab.moreNavigationController
            
            if let top = moreNavigationController.topViewController, top.view.window != nil {
                return topViewController(base: top)
            } else if let selected = tab.selectedViewController {
                return topViewController(base: selected)
            }
        }
        
        if let presented = base?.presentedViewController {
            return topViewController(base: presented)
        }
        
        return base
    }
}

private func createHomeUiControllerComponent(
    applicationComponent: IosApplicationComponent
) -> HomeUiControllerComponent {
    return HomeUiControllerComponent.companion.create(
        applicationComponent: applicationComponent
    )
}

class FirebaseAnalytics: AccountBookAnalytics {
    func trackScreenView(name: String, arguments: [String: Any]?) {
        var params = [AnalyticsParameterScreenName: name]
        arguments?.forEach { key, value in
            params[key] = "screen_arg_\(value)"
        }
        
        Analytics.logEvent(AnalyticsEventSelectContent, parameters: params)
    }
    
    func setEnabled(enabled: Bool) {
        Analytics.setAnalyticsCollectionEnabled(enabled)
    }
}
