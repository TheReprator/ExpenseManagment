package dev.reprator.accountbook.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform