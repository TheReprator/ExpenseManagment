package dev.reprator.accountbook.splash

import androidx.compose.runtime.Immutable

@Immutable
data class ModalSplashState(val languageList: List<ModalLanguage>, val imageList: List<String>)

data class ModalLanguage(val language: String, val id: Int)