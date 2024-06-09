// Copyright 2018, Christopher Banes and the Tivi project contributors
// SPDX-License-Identifier: Apache-2.0

package app.tivi.episode.track

import androidx.compose.runtime.Immutable
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState

@Immutable
data class LanguageUiState(
  val season: Season? = null,
  val episode: Episode? = null,
  val showEpisodeInfo: Boolean = true,
  val refreshing: Boolean = false,
  val message: UiMessage? = null,

  val canSubmit: Boolean = false,
  val submitInProgress: Boolean = false,

  val showSetFirstAired: Boolean = false,
  val selectedDate: LocalDate,
  val selectedTime: LocalTime,

  val eventSink: (LanguageUiEvent) -> Unit,
) : CircuitUiState

sealed interface LanguageUiEvent : CircuitUiEvent {
  data class Refresh(val fromUser: Boolean = false) : LanguageUiEvent
  object Submit : LanguageUiEvent
  object SelectNow : LanguageUiEvent
  object SelectFirstAired : LanguageUiEvent
  data class SelectDate(val date: LocalDate) : LanguageUiEvent
  data class SelectTime(val time: LocalTime) : LanguageUiEvent
  data class ClearMessage(val id: Long) : LanguageUiEvent
}
