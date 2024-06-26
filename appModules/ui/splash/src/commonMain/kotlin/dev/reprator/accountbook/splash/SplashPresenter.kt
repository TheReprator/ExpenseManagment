package dev.reprator.accountbook.splash

import androidx.compose.runtime.*
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import dev.reprator.accountbook.splash.domain.usecase.SplashUseCase
import dev.reprator.accountbook.splash.modals.ModalStateSplash
import dev.reprator.appFeatures.api.utility.InternetChecker
import dev.reprator.core.util.onException
import dev.reprator.screens.SplashScreen
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class SplashUiPresenterFactory(
    private val presenterFactory: (Navigator) -> AccountPresenter,
) : Presenter.Factory {

    override fun create(screen: Screen, navigator: Navigator, context: CircuitContext): Presenter<*>? {
        return when (screen) {
            is SplashScreen -> presenterFactory(navigator)
            else -> null
        }
    }
}

@Inject
class AccountPresenter(
    @Assisted private val navigator: Navigator,
    private val splashUseCase: SplashUseCase,
    private val internetChecker: InternetChecker,
) : Presenter<SplashUiState> {

    @Composable
    override fun present(): SplashUiState {

        var splashData by rememberRetained { mutableStateOf(ModalStateSplash(emptyList(), emptyList())) }
        val isLoading by splashUseCase.inProgress.collectAsState(false)

        val scope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            val result = splashUseCase.invoke(Unit)

            if (internetChecker.networkStatus.value) {
                splashData = result.getOrDefault(ModalStateSplash(emptyList(), emptyList()))
            }

            result.onException { e ->
                println("Splash presenter error: ${e.message}")
            }
        }

        return SplashUiState(
            data = splashData,
        ) { event ->
            when (event) {
                SplashUiEvent.Reload -> {
                    print("Splash presenter action: reload")
                }

                SplashUiEvent.NavigateToDashBoard, SplashUiEvent.NavigateToLogin -> scope.launch {
                    println("Splash presenter error: navigate")
                }

//                SplashUiEvent.NavigateLToSettings -> {
//                    navigator.goTo(SettingsScreen)
//                }
            }
        }
    }
}
