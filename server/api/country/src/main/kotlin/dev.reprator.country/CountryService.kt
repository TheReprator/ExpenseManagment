package dev.reprator.country

import org.koin.core.KoinApplication
import org.koin.ksp.generated.*

fun KoinApplication.setUpKoinCountry() {
    modules(CountryModule().module)
}
