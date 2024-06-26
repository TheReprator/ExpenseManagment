package dev.reprator.accountbook.splash

import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import dev.reprator.accountbook.splash.modals.ModalStateSplash

data class SplashUiState(
    val data: ModalStateSplash,
    val eventSink: (SplashUiEvent) -> Unit,
) : CircuitUiState


sealed interface SplashUiEvent : CircuitUiEvent {
    data object Reload : SplashUiEvent
    data object NavigateToDashBoard : SplashUiEvent
    data object NavigateToLogin : SplashUiEvent
}
