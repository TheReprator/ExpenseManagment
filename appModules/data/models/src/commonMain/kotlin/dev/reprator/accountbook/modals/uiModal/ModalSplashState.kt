package dev.reprator.accountbook.modals.uiModal


data class ModalSplashState(val languageList: List<ModalLanguage>, val imageList: List<String>)

data class ModalLanguage(val language: String, val id: Int)