package dev.reprator.accountbook

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import dev.reprator.accountbook.inject.DesktopApplicationComponent
import dev.reprator.accountbook.inject.WindowComponent
import dev.reprator.accountbook.inject.create
import dev.reprator.screens.SplashScreen
import kotlinx.coroutines.delay

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
        val component = remember(applicationComponent) {
            WindowComponent.create(applicationComponent)
        }

        DisposableEffect(Unit) {
            val listener = WindowStateListener(applicationComponent.applicationLifeCycle)
            window.addWindowListener(listener)
            onDispose {
                window.removeWindowListener(listener)
            }
        }

        LaunchedEffect(Unit) {
            delay(2000)
            applicationComponent.applicationLifeCycle.isAppInForeGround()
        }

        val backstack = rememberSaveableBackStack(listOf(SplashScreen))
        val navigator = rememberCircuitNavigator(backstack) { /* no-op */ }

        component.accountBookContent.Content(
            backstack = backstack,
            navigator = navigator,
            onOpenUrl = { /* no-op for now */ },
            modifier = Modifier,
        )
    }
}
