package dev.reprator.accountbook.developer.log

import androidx.compose.runtime.Immutable
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import dev.reprator.appFeatures.api.logger.LogMessage

@Immutable
data class DevLogUiState(
    val logs: List<LogMessage>,
    val eventSink: (DevLogUiEvent) -> Unit,
) : CircuitUiState

sealed interface DevLogUiEvent : CircuitUiEvent {
    data object NavigateUp : DevLogUiEvent
}
