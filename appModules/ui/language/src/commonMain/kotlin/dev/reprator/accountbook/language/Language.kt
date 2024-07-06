package dev.reprator.accountbook.language

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import dev.reprator.accountbook.language.modals.ModalStateLanguage
import dev.reprator.baseUi.ui.*
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
    viewState: LanguageUiState,
    modifier: Modifier = Modifier,
) {
    println( "VikramLanguagePresenter00:: loading: ${viewState.isLoading}, errorMessage: ${viewState.message}, languageData: ${viewState.data}" )

    Language(
        viewState = viewState,
        onReload = { viewState.eventSink(LanguageUiEvent.Reload) },
        onRemoveError = { id ->
            viewState.eventSink(LanguageUiEvent.ClearMessage(id))
            viewState.eventSink(LanguageUiEvent.Reload)
        },
        onClose = { },
        onSubmit = { },
        onLanguageUpdate = { id -> viewState.eventSink(LanguageUiEvent.UpdateSelectedLanguage(id)) },
        modifier = modifier,
    )
}

@Composable
internal fun Language(
    viewState: LanguageUiState,
    onReload: () -> Unit,
    onRemoveError: (id: Long) -> Unit,
    onClose: () -> Unit,
    onSubmit: () -> Unit,
    onLanguageUpdate: (id: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .testTag("accountbook_testTag_languageSelection"),
    ) {

        if (viewState.isLoading) {
            LanguageLoader()
        }

        if ((null != viewState.message) && (viewState.message.message.isNotBlank())) {
            Error(viewState.message.message) {
                onRemoveError(viewState.message.id)
            }
        }

        if (viewState.data.isEmpty() && (!viewState.isLoading)) {
            LanguageEmpty()
        } else
            LanguageUi(
                viewState.data,
                {
                    println("Submit clicked")
                },
                modifier = modifier,
            )

    }
}

@Composable
private fun LanguageLoader() {
    AppLoader()
}

@Composable
private fun LanguageEmpty() {
    EmptyContent(
        title = { Text(text = "Empty Language") },
        prompt = { Text(text = "No Language Fetched") },
        graphic = { Text(text = "\uD83D\uDCFC") },
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 64.dp),
    )
}

@Composable
private fun Error(errorDescription: String, onButtonClick: () -> Unit) {
    ErrorContent(buttonName = "Retry", errorDescription = errorDescription, onButtonClick = onButtonClick)
}

@Composable
private fun LanguageUi(
    languageList: List<ModalStateLanguage>,
    languageSelection: () -> Unit,
    modifier: Modifier = Modifier
) {

    val paddingModifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 20.dp, bottom = 20.dp)

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer).padding(20.dp).fillMaxWidth()
            .requiredHeight(280.dp)
        // .verticalScroll(rememberScrollState())
    ) {
//
//        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
//
//            Text("Select your language", textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
//            IconButton(onClick = { }) {
//                Icon(Icons.Filled.Close, contentDescription = "Close")
//            }
//        }
//
//        LanguageContainerList(languageList, {})
//
//        Button(onClick = {}, modifier = Modifier.padding(start = 70.dp, end = 70.dp).fillMaxWidth()) {
//            Text("Submit")
//        }
        Text("Submit")
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