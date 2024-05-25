package dev.reprator.accountbook.home

import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.LocalUIViewController
import androidx.compose.ui.uikit.ComposeUIViewControllerDelegate
import androidx.compose.ui.window.ComposeUIViewController
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import me.tatarka.inject.annotations.Inject
import platform.Foundation.NSURL
import platform.SafariServices.SFSafariViewController
import platform.UIKit.UIViewController
import dev.reprator.screens.SplashScreen

typealias AccountBookUiViewController = () -> UIViewController

@Inject
@Suppress("ktlint:standard:function-naming")
fun AccountBookUiViewController(
    accountBookContent: AccountBookContent,
): UIViewController = ComposeUIViewController (configure = {
    /**
     * A delegate to track composeViewControllers lifecycle callbacks
     */
    delegate = object : ComposeUIViewControllerDelegate {
        override fun viewDidAppear(animated: Boolean) {
            super.viewDidAppear(animated)
            println("Vikram::LifeCycle::ios, viewDidAppear")
        }
        override fun viewDidLoad() {
            super.viewDidLoad()
            println("Vikram::LifeCycle::ios, viewDidLoad")
        }
        override fun viewWillDisappear(animated: Boolean) {
            super.viewWillDisappear(animated)
            println("Vikram::LifeCycle::ios, viewWillDisappear")
        }
        override fun viewWillAppear(animated: Boolean) {
            super.viewWillAppear(animated)
            println("Vikram::LifeCycle::ios, viewWillAppear")
        }
        override fun viewDidDisappear(animated: Boolean) {
            super.viewDidDisappear(animated)
            println("Vikram::LifeCycle::ios, viewDidDisappear")
        }
    }
}){
    val backstack = rememberSaveableBackStack(listOf(SplashScreen))
    val navigator = rememberCircuitNavigator(backstack, onRootPop = { /* no-op */ })
    val uiViewController = LocalUIViewController.current

    accountBookContent.Content(
        backstack = backstack,
        navigator = navigator,
        onOpenUrl = { url ->
            val safari = SFSafariViewController(NSURL(string = url))
            uiViewController.presentViewController(safari, animated = true, completion = null)
        },
        modifier = Modifier,
    )
}
