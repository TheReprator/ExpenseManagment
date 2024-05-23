package dev.reprator.accountbook.home

import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.LocalUIViewController
import androidx.compose.ui.window.ComposeUIViewController
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import me.tatarka.inject.annotations.Inject
import platform.Foundation.NSURL
import platform.SafariServices.SFSafariViewController
import platform.UIKit.UIViewController
import dev.reprator.screens.SplashScreen

typealias accountBookUiViewController = () -> UIViewController

@Inject
@Suppress("ktlint:standard:function-naming")
fun accountBookUiViewController(
    accountBookContent: AccountBookContent,
): UIViewController = ComposeUIViewController {
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
