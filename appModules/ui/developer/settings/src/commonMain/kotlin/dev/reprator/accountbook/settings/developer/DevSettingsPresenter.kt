package dev.reprator.accountbook.settings.developer

import androidx.compose.runtime.Composable
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import dev.reprator.screens.DevLogScreen
import dev.reprator.screens.DevSettingsScreen
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class DevSettingsUiPresenterFactory(
    private val presenterFactory: (Navigator) -> DevSettingsPresenter,
) : Presenter.Factory {
    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext,
    ): Presenter<*>? = when (screen) {
        is DevSettingsScreen -> presenterFactory(navigator)
        else -> null
    }
}

@Inject
class DevSettingsPresenter(
    @Assisted private val navigator: Navigator,
) : Presenter<DevSettingsUiState> {

    @Composable
    override fun present(): DevSettingsUiState {

        fun eventSink(event: DevSettingsUiEvent) {
            when (event) {
                DevSettingsUiEvent.NavigateUp -> navigator.pop()
                DevSettingsUiEvent.NavigateLog -> navigator.goTo(DevLogScreen)
            }
        }

        return DevSettingsUiState(
            eventSink = ::eventSink,
        )
    }
}
