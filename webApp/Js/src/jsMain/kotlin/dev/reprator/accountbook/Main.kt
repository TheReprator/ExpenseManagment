package dev.reprator.accountbook

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import org.jetbrains.skiko.wasm.onWasmReady
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import dev.reprator.accountbook.inject.JsApplicationComponent
import dev.reprator.accountbook.inject.WindowComponent
import dev.reprator.accountbook.inject.create
import dev.reprator.screens.SplashScreen

@OptIn(ExperimentalComposeUiApi::class)
fun main() = onWasmReady {
    CanvasBasedWindow(canvasElementId = "ComposeTarget") {

        val applicationComponent = remember {
            JsApplicationComponent.create()
        }

        LaunchedEffect(applicationComponent) {
            applicationComponent.initializers.initialize()
        }

        val component = remember(applicationComponent) {
            WindowComponent.create(applicationComponent)
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