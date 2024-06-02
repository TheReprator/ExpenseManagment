package dev.reprator.accountbook.splash

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject
import androidx.compose.foundation.background
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Alignment
import dev.reprator.screens.SplashScreen

@Inject
class SplashUiFactory : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
        is SplashScreen -> {
            ui<SplashUiState> { state, modifier ->
                SplashUi(state, modifier)
            }
        }

        else -> null
    }
}


@Composable
internal fun SplashUi(
    state: SplashUiState,
    modifier: Modifier = Modifier,
) {
    val eventSink = state.eventSink

    SplashUi(
        viewState = state,
        login = { eventSink(SplashUiEvent.NavigateLToSettings) },
        modifier = modifier,
    )
}

@Composable
internal fun SplashUi(
    viewState: SplashUiState,
    login: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            login()
        }, Modifier.background(MaterialTheme.colorScheme.primary)) {
            Text("Click me!", style = MaterialTheme.typography.titleLarge)
        }
    }
}