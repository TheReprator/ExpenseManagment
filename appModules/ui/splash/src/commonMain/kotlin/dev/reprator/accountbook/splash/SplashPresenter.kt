package dev.reprator.accountbook.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import dev.reprator.accountbook.splash.domain.usecase.SplashUseCase
import dev.reprator.accountbook.splash.modals.ModalStateSplash
import dev.reprator.appFeatures.api.logger.Logger
import dev.reprator.baseUi.ui.UiMessage
import dev.reprator.baseUi.ui.UiMessageManager
import dev.reprator.baseUi.ui.rememberCoroutineScope
import dev.reprator.screens.SplashScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class SplashUiPresenterFactory(
    private val presenterFactory: (Navigator) -> AccountPresenter,
) : Presenter.Factory {

    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext
    ): Presenter<*>? {
        return when (screen) {
            is SplashScreen -> presenterFactory(navigator)
            else -> null
        }
    }
}

@Inject
class AccountPresenter(
    @Assisted private val navigator: Navigator,
    private val splashUseCase: Lazy<SplashUseCase>,
    private val logger: Lazy<Logger>
) : Presenter<SplashUiState> {

    @Composable
    override fun present(): SplashUiState {

        val scope = rememberCoroutineScope()
        val uiMessageManager = remember { UiMessageManager() }

        var splashData by rememberRetained {
            mutableStateOf(
                ModalStateSplash(
                    emptyList(),
                    emptyList()
                )
            )
        }

        val loading by splashUseCase.value.inProgress.collectAsState(true)
        val message by uiMessageManager.message.collectAsState(null)

        fun eventSink(event: SplashUiEvent) {

            when (event) {

                is SplashUiEvent.ClearMessage -> {
                    scope.launch {
                        uiMessageManager.clearMessage(event.id)
                    }
                }

                SplashUiEvent.Reload -> {

                    scope.launch {
                        logger.value.e { "VikramSplashPresenter:: loading: $loading, errorMessage: $message, splashData: $splashData, LanguagePresenter: ${this@AccountPresenter}" }
                        val result = splashUseCase.value.invoke(Unit)
                        result.onFailure { e ->
                            logger.value.i(e)
                            uiMessageManager.emitMessage(UiMessage(e))
                        }

                        splashData = result.getOrDefault(ModalStateSplash(emptyList(), emptyList()))

                        logger.value.e { "VikramSplashPresenter2:: loading: $loading, errorMessage: $message, splashData: $splashData, LanguagePresenter: ${this@AccountPresenter}" }
                    }
                }

                SplashUiEvent.NavigateToDashBoard, SplashUiEvent.NavigateToLogin -> scope.launch {
                    println("Splash presenter error: navigate")
                }

            }
        }

        LaunchedEffect(Unit) {
            eventSink(SplashUiEvent.Reload)
            logger.value.e { "VikramSplashPresenter3:: loading: $loading, errorMessage: $message, splashData: $splashData, LanguagePresenter: ${this@AccountPresenter}" }
        }

        return SplashUiState(
            data = splashData,
            isLoading =loading,
            message = message,
            eventSink = ::eventSink
        )
    }
}
