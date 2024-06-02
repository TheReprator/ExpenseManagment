package dev.reprator.base_ktor.util

import io.ktor.server.config.*

fun ApplicationConfig.propertyConfig(property: String): String {
    return this.property(property).getString()
}