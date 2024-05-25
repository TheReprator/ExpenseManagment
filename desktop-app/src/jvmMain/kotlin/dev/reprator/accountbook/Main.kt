package dev.reprator.accountbook

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
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent

public class OpenNewWindowWindowListener : WindowAdapter() {
    override fun windowActivated(e: WindowEvent?) {
        super.windowActivated(e)
        println("vikram::DEsktop:: windowActivated:: ${e.toString()}")
    }

    override fun windowClosed(e: WindowEvent?) {
        super.windowClosed(e)
        println("vikram::DEsktop:: windowClosed:: ${e.toString()}")
    }

    override fun windowClosing(e: WindowEvent?) {
        super.windowClosing(e)
        println("vikram::DEsktop:: windowClosing:: ${e.toString()}")
    }

    override fun windowDeactivated(e: WindowEvent?) {
        super.windowDeactivated(e)
        println("vikram::DEsktop:: windowDeactivated:: ${e.toString()}")
    }

    override fun windowDeiconified(e: WindowEvent?) {
        super.windowDeiconified(e)
        println("vikram::DEsktop:: windowDeiconified:: ${e.toString()}")
    }

    override fun windowGainedFocus(e: WindowEvent?) {
        super.windowGainedFocus(e)
        println("vikram::DEsktop:: windowGainedFocus:: ${e.toString()}")
    }

    override fun windowIconified(e: WindowEvent?) {
        super.windowIconified(e)
        println("vikram::DEsktop:: windowIconified:: ${e.toString()}")
    }

    override fun windowLostFocus(e: WindowEvent?) {
        super.windowLostFocus(e)
        println("vikram::DEsktop:: windowLostFocus:: ${e.toString()}")
    }

    override fun windowOpened(e: WindowEvent?) {
        super.windowOpened(e)
        println("vikram::DEsktop:: windowOpened:: ${e.toString()}")
    }

    override fun windowStateChanged(e: WindowEvent?) {
        super.windowStateChanged(e)
        println("vikram::DEsktop:: windowStateChanged:: ${e.toString()}")
    }
}


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

            window.addWindowListener(OpenNewWindowWindowListener())

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
