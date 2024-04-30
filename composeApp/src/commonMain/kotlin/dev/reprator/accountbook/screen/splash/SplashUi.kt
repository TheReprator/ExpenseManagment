package dev.reprator.accountbook.screen.splash

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import dev.reprator.accountbook.screen.navigation.SplashScreen
import me.tatarka.inject.annotations.Inject
import accountbook_kmp.composeapp.generated.resources.Res
import accountbook_kmp.composeapp.generated.resources.compose_multiplatform
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import dev.reprator.accountbook.expect.Greeting
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

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
    login = { eventSink(SplashUiEvent.NavigateToLogin) },
    modifier = modifier,
  )
}

@Composable
internal fun SplashUi(
  viewState: SplashUiState,
  login: () -> Unit,
  modifier: Modifier = Modifier,
) {
  var showContent by remember { mutableStateOf(false) }
  
    Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
      Button(onClick = {
        showContent = !showContent
      
      }, Modifier.background(MaterialTheme.colorScheme.primary)) {
        Text("Click me!", style = MaterialTheme.typography.titleLarge)
      }
  
      AnimatedVisibility(showContent) {
        val greeting = remember { Greeting().greet() }
        Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
          Image(painterResource(Res.drawable.compose_multiplatform), null)
          Text("Compose: $greeting", style = MaterialTheme.typography.titleLarge)
        }
      }
    }
}
