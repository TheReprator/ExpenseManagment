package dev.reprator.accountbook.language

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import dev.reprator.accountbook.language.modals.ModalStateLanguage
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
        state.data,
        {
            println("Submit clicked")
        },
        modifier = modifier,
    )
}

@Composable
private fun LanguageUi(languageList: List<ModalStateLanguage>, submit: () -> Unit, modifier: Modifier = Modifier) {

    val paddingModifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 20.dp, bottom = 20.dp)

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer).padding(20.dp).fillMaxWidth()
            .requiredHeight(280.dp)
        // .verticalScroll(rememberScrollState())
    ) {

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {

            Text("Select your language", textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
            IconButton(onClick = { }) {
                Icon(Icons.Filled.Close, contentDescription = "Close")
            }
        }


        LanguageContainerList(languageList, {})

        Button(onClick = submit, modifier = Modifier.padding(start = 70.dp, end = 70.dp).fillMaxWidth()) {
            Text("Submit")
        }
    }
}

@Composable
private fun LanguageContainerList(
    languageList: List<ModalStateLanguage>,
    itemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp)
    ) {
        items(languageList.size) { position ->
            LanguageItem(languageList[position], {})
        }
    }
}

@Composable
private fun LanguageItem(language: ModalStateLanguage, itemClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
    ) {
        Text(text = language.language, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
    }

}