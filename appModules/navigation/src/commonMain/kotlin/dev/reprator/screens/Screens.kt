package dev.reprator.screens

import com.slack.circuit.runtime.screen.Screen

@Parcelize
object SplashScreen : AccountBookScreen(name = "SplashScreen()")

@Parcelize
object SettingsScreen : AccountBookScreen(name = "Settings()")

@Parcelize
object DevSettingsScreen : AccountBookScreen(name = "DevelopmentSettings()")

@Parcelize
object DevLogScreen : AccountBookScreen(name = "DevelopmentLog()")

@Parcelize
data class LanguageScreen(val id: Long) : AccountBookScreen(name = "LanguageScreen()") {
  override val arguments get() = mapOf("id" to id)
}

@Parcelize
data class UrlScreen(val url: String) : AccountBookScreen(name = "UrlScreen()") {
    override val arguments get() = mapOf("url" to url)
}

abstract class AccountBookScreen(val name: String) : Screen {
    open val arguments: Map<String, *>? = null
}
