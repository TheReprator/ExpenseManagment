package dev.reprator.accountbook.language

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import dev.reprator.accountbook.language.domain.usecase.LanguageUseCase
import dev.reprator.accountbook.language.modals.ModalStateLanguage
import dev.reprator.appFeatures.api.logger.Logger
import dev.reprator.baseUi.ui.rememberCoroutineScope
import dev.reprator.core.util.onException
import dev.reprator.screens.LanguageScreen
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


    @Composable
    override fun present(): LanguageUiState {
        val scope = rememberCoroutineScope()

        var selectedLanguage by rememberSaveable { mutableStateOf(screen.id) }


        var languageData by rememberRetained { mutableStateOf(emptyList<ModalStateLanguage>()) }
        val isLoading by useCase.inProgress.collectAsState(false)


        LaunchedEffect(Unit) {
            val result = useCase(Unit)

            languageData = result.getOrDefault(emptyList())


            result.onException { e ->
                println("language presenter error: ${e.message}")
            }
        }

//        LaunchedEffect(Unit) {
//            observeEpisodeDetails.value.invoke(ObserveEpisodeDetails.Params(screen.id))
//            eventSink(EpisodeTrackUiEvent.Refresh(false))
//        }

        return LanguageUiState(
            data = languageData,{

            }
        )
    }
}
