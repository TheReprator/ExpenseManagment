package dev.reprator.accountbook.language.modals

import androidx.compose.runtime.Immutable


@Immutable
data class ModalStateLanguage(val language: String, val id: Int, val isSelected: Boolean = false)