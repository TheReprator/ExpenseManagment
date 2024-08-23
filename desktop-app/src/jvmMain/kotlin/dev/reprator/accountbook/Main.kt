package dev.reprator.accountbook

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import dev.reprator.accountbook.inject.DesktopApplicationComponent
import dev.reprator.accountbook.inject.WindowComponent
import dev.reprator.accountbook.inject.create
import dev.reprator.screens.SplashScreen
import java.awt.Dimension

fun main() = application {
    val applicationComponent = remember {
        DesktopApplicationComponent.create()
    }

    LaunchedEffect(applicationComponent) {
        applicationComponent.initializers.initialize()
    }

    Window(
        title = "AccountBook",
        onCloseRequest = ::exitApplication,
    ) {
        
        window.minimumSize = Dimension(300, 400)

        val component = remember(applicationComponent) {
            WindowComponent.create(applicationComponent)
        }

        DisposableEffect(Unit) {
            val listener = WindowStateListener(applicationComponent.applicationLifeCycle)
            window.addWindowListener(listener)
            applicationComponent.applicationLifeCycle.isAppInForeGround()
            onDispose {
                window.removeWindowListener(listener)
            }
        }

        val backstack = rememberSaveableBackStack(listOf(SplashScreen))
        val navigator = rememberCircuitNavigator(backstack) { /* no-op */ }

        component.accountBookContent.Content(
            backstack = backstack,
            navigator = navigator,
            onOpenUrl = { /* no-op for now */
                            false
                        },
            modifier = Modifier,
        )
    }
}
