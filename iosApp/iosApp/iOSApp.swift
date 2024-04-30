import ComposeApp
import SwiftUI

class AppDelegate: UIResponder, UIApplicationDelegate {
	lazy var applicationComponent: IosApplicationComponent = createApplicationComponent(
		appDelegate: self
	)

	func application(
		_: UIApplication,
		didFinishLaunchingWithOptions _: [UIApplication.LaunchOptionsKey: Any]? = nil
	) -> Bool {
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
}


@main
struct iOSApp: App {
	 @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate

    var body: some Scene {
		WindowGroup {
			let uiComponent = createHomeUiControllerComponent(
				applicationComponent: delegate.applicationComponent
			)
			ContentView(component: uiComponent)
		}
	}
}

private func createApplicationComponent(
	appDelegate: AppDelegate
) -> IosApplicationComponent {
	return IosApplicationComponent.companion.create()
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
