package dev.reprator.accountbook.language

import androidx.compose.runtime.Immutable
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import dev.reprator.accountbook.language.modals.ModalStateLanguage
import dev.reprator.baseUi.ui.UiMessage

@Immutable
data class LanguageUiState(
    val data: List<ModalStateLanguage>,
    val isLoading: Boolean = false,
    val message: UiMessage? = null,
    val eventSink: (LanguageUiEvent) -> Unit,
) : CircuitUiState


sealed interface LanguageUiEvent : CircuitUiEvent {
    data class ClearMessage(val id: Long) : LanguageUiEvent
    data object Reload : LanguageUiEvent
    data class UpdateSelectedLanguage(val languageId: Long) : LanguageUiEvent
}

