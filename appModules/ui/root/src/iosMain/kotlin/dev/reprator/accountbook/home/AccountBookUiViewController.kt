package dev.reprator.accountbook.home

import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.LocalUIViewController
import androidx.compose.ui.platform.AccessibilityDebugLogger
import androidx.compose.ui.platform.AccessibilitySyncOptions
import androidx.compose.ui.window.ComposeUIViewController
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import dev.reprator.appFeatures.api.logger.Logger
import dev.reprator.core.app.ApplicationInfo
import dev.reprator.screens.SplashScreen
import me.tatarka.inject.annotations.Inject
import platform.Foundation.NSURL
import platform.SafariServices.SFSafariViewController
import platform.UIKit.UIViewController

typealias AccountBookUiViewController = () -> UIViewController

@OptIn(ExperimentalComposeApi::class)
@Inject
fun AccountBookUiViewController(
    accountBookContent: AccountBookContent,
    logger: Logger,
    applicationInfo: ApplicationInfo,
): UIViewController = ComposeUIViewController(
    configure = {
        val a11yLogger = object : AccessibilityDebugLogger {
            override fun log(message: Any?) {
                logger.d { "AccessibilityDebugLogger: $message" }
            }
        }

        accessibilitySyncOptions = when {
            applicationInfo.debugBuild -> AccessibilitySyncOptions.Always(a11yLogger)
            else -> AccessibilitySyncOptions.WhenRequiredByAccessibilityServices(a11yLogger)
        }
    },
) {
    val backstack = rememberSaveableBackStack(listOf(SplashScreen))
    val navigator = rememberCircuitNavigator(backstack, onRootPop = { /* no-op */ })
    val uiViewController = LocalUIViewController.current

    accountBookContent.Content(
        backstack = backstack,
        navigator = navigator,
        onOpenUrl = { url ->
            val safari = SFSafariViewController(NSURL(string = url))
            uiViewController.presentViewController(safari, animated = true, completion = null)
            true
        },
        modifier = Modifier,
    )
}