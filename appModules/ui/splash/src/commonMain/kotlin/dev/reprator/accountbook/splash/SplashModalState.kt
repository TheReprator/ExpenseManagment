package dev.reprator.accountbook.splash

import androidx.compose.runtime.Immutable

@Immutable
data class SplashModalState(val languageList: List<SplashModalLanguage>, val imageList: List<String>)

data class SplashModalLanguage(val language: String, val id: Int)