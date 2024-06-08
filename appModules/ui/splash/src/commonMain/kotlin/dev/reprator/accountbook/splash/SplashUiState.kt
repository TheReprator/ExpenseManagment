package dev.reprator.accountbook.splash

import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState

data class SplashUiState(
    val data: SplashModalState,
    val eventSink: (SplashUiEvent) -> Unit,
) : CircuitUiState


sealed interface SplashUiEvent : CircuitUiEvent {
    data object Reload : SplashUiEvent
    data object NavigateToDashBoard : SplashUiEvent
    data object NavigateToLogin : SplashUiEvent
}
