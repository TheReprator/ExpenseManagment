package dev.reprator.accountbook.language

import androidx.compose.runtime.Immutable
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import dev.reprator.accountbook.language.modals.ModalStateLanguage

@Immutable
data class LanguageUiState(
    val data: List<ModalStateLanguage>,
    val eventSink: (LanguageUiEvent) -> Unit,
) : CircuitUiState


sealed interface LanguageUiEvent : CircuitUiEvent {
    data class UpdateSelectedLanguage(val languageId: Long) : LanguageUiEvent
}

