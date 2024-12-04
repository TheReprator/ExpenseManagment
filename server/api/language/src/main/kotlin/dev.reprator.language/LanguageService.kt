package dev.reprator.language

import org.koin.core.KoinApplication
import org.koin.ksp.generated.*

fun KoinApplication.setUpKoinLanguage() {
    modules(LanguageModule().module)
}
