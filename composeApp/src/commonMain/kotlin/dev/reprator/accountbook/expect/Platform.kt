package dev.reprator.accountbook.expect

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform