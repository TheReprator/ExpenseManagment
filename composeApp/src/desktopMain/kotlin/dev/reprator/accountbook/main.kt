package dev.reprator.accountbook

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import dev.reprator.accountbook.di.DesktopApplicationComponent
import dev.reprator.accountbook.di.WindowComponent
import dev.reprator.accountbook.di.create
import dev.reprator.accountbook.screen.navigation.SplashScreen

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

        val backstack = rememberSaveableBackStack(listOf(SplashScreen))
        val navigator = rememberCircuitNavigator(backstack) { /* no-op */ }

        component.accountBookContent.Content(
            backstack,
            navigator,
            { /* no-op for now */ },
            Modifier,
        )
    }
}
