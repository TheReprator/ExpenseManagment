package dev.reprator.accountbook.splash

import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import dev.reprator.accountbook.splash.modals.ModalStateSplash
import dev.reprator.baseUi.ui.UiMessage

data class SplashUiState(
    val data: ModalStateSplash,
    val isLoading: Boolean = false,
    val message: UiMessage? = null,
    val eventSink: (SplashUiEvent) -> Unit,
) : CircuitUiState

sealed interface SplashUiEvent : CircuitUiEvent {
    data class ClearMessage(val id: Long) : SplashUiEvent
    data object Reload : SplashUiEvent
    data object NavigateToDashBoard : SplashUiEvent
    data object NavigateToLogin : SplashUiEvent
}
