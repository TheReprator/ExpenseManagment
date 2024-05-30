package dev.reprator.accountbook.splash.expect

class Jslatform : Platform {
    override val name: String = "JS"
}

actual fun getPlatform(): Platform = Jslatform()
