package dev.reprator.accountbook.splash

import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState

data class SplashUiState(
    val data: ModalSplashState,
    val eventSink: (SplashUiEvent) -> Unit,
) : CircuitUiState


sealed interface SplashUiEvent : CircuitUiEvent {
    object Reload : SplashUiEvent
    object NavigateToDashBoard : SplashUiEvent
    object NavigateToLogin : SplashUiEvent

    data object NavigateLToSettings : SplashUiEvent
}
