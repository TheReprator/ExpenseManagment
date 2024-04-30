package dev.reprator.accountbook.screen.navigation

import com.slack.circuit.runtime.screen.Screen

@CommonParcelize
object SplashScreen : AccountBookScreen(name = "SplashUi()")

@CommonParcelize
data class UrlScreen(val url: String) : AccountBookScreen(name = "UrlScreen()") {
  override val arguments get() = mapOf("url" to url)
}

abstract class AccountBookScreen(val name: String) : Screen {
  open val arguments: Map<String, *>? = null
}
