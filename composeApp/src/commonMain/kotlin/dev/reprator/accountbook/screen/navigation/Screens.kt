package dev.reprator.accountbook.screen.navigation

import com.slack.circuit.runtime.screen.Screen

@Parcelize
object SplashScreen : AccountBookScreen(name = "SplashUi()")

@Parcelize
data class UrlScreen(val url: String) : AccountBookScreen(name = "UrlScreen()") {
    override val arguments get() = mapOf("url" to url)
}

abstract class AccountBookScreen(val name: String) : Screen {
    open val arguments: Map<String, *>? = null
}
