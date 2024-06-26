package dev.reprator.accountbook.splash.modals

import androidx.compose.runtime.Immutable

@Immutable
data class ModalStateSplash(val languageList: List<ModalStateLanguage>, val imageList: List<String>)

@Immutable
data class ModalStateLanguage(val language: String, val id: Int)