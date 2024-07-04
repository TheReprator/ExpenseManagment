package dev.reprator.accountbook.language

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import dev.reprator.accountbook.language.domain.usecase.LanguageUseCase
import dev.reprator.accountbook.language.modals.ModalStateLanguage
import dev.reprator.appFeatures.api.logger.Logger
import dev.reprator.baseUi.ui.UiMessage
import dev.reprator.baseUi.ui.UiMessageManager
import dev.reprator.baseUi.ui.rememberCoroutineScope
import dev.reprator.screens.LanguageScreen
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class LanguageUiPresenterFactory(
    private val presenterFactory: (LanguageScreen, Navigator) -> LanguagePresenter,
) : Presenter.Factory {
    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext,
    ): Presenter<*>? = when (screen) {
        is LanguageScreen -> presenterFactory(screen, navigator)
        else -> null
    }
}

@Inject
class LanguagePresenter(
    @Assisted private val screen: LanguageScreen,
    @Assisted private val navigator: Navigator,
    private val useCase: LanguageUseCase,
    private val logger: Logger,
) : Presenter<LanguageUiState> {

    var i = 0

    @Composable
    override fun present(): LanguageUiState {
        val scope = rememberCoroutineScope()
        val uiMessageManager = remember { UiMessageManager() }

        var languageData by rememberRetained { mutableStateOf(emptyList<ModalStateLanguage>()) }

        val loading by useCase.inProgress.collectAsState(false)
        val message by uiMessageManager.message.collectAsState(null)

//
//        LaunchedEffect(Unit) {
//
//            val result = useCase.invoke(Unit)
//            languageData= result.getOrDefault(emptyList())
//
//
//            result.onFailure { e ->
//                logger.i(e)
//                uiMessageManager.emitMessage(UiMessage(e))
//                println("language presenter error: ${e.message}")
//            }
//        }

        fun eventSink(event: LanguageUiEvent) {
            when (event) {
                is LanguageUiEvent.ClearMessage -> {
                    scope.launch {
                        uiMessageManager.clearMessage(event.id)
                    }
                }

                is LanguageUiEvent.Reload -> {
                    scope.launch {
                        i=0
                        val result = useCase.invoke(Unit).also {
                            it.onFailure { e ->
                                logger.i(e)
                                uiMessageManager.emitMessage(UiMessage(e))
                                println("language presenter error: ${e.message}")
                            }
                        }.getOrDefault(emptyList()).map {
                            if (screen.id == it.id)
                                it.copy(isSelected = true)
                            else
                                it
                        }

                        languageData = result
                    }
                }

                is LanguageUiEvent.UpdateSelectedLanguage -> {

                }
            }
        }

        LaunchedEffect(Unit) {
            logger.e { "VikramLanguage11:: loading = ${loading}, i = $i, message =$message, data = $languageData" }
            eventSink(LanguageUiEvent.Reload)
        }

        i++
        logger.e { "VikramLanguage:: loading = ${loading}, i = $i, message =$message, data = $languageData" }
        return LanguageUiState(
            data = languageData,
            isLoading = loading,
            message = message,
            eventSink = ::eventSink
        )
    }
}
