package dev.reprator.accountbook.splash.expect

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform