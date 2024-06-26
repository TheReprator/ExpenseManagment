package dev.reprator.accountbook.settings.developer

import androidx.compose.runtime.Immutable
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState

@Immutable
data class DevSettingsUiState(
    val eventSink: (DevSettingsUiEvent) -> Unit,
) : CircuitUiState

sealed interface DevSettingsUiEvent : CircuitUiEvent {
    data object NavigateUp : DevSettingsUiEvent
    data object NavigateLog : DevSettingsUiEvent
}
