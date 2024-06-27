package dev.reprator.accountbook.language

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import dev.reprator.screens.LanguageScreen
import me.tatarka.inject.annotations.Inject

@Inject
class LanguageUiFactory : Ui.Factory {
  override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
    is LanguageScreen -> {
      ui<LanguageUiState> { state, modifier ->
        Language(state, modifier)
      }
    }

    else -> null
  }
}

@Composable
internal fun Language(
  state: LanguageUiState,
  modifier: Modifier = Modifier,
) {
  // Need to extract the eventSink out to a local val, so that the Compose Compiler
  // treats it as stable. See: https://issuetracker.google.com/issues/256100927
  val eventSink = state.eventSink

  LanguageUi(
    modifier = modifier,
  )
}

@Composable
private fun LanguageUi(modifier: Modifier = Modifier) {

    val paddingModifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 20.dp, bottom = 20.dp)

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.background(MaterialTheme.colorScheme.primaryContainer).fillMaxSize()
    ) {

        Text("Vikram")
    }
}