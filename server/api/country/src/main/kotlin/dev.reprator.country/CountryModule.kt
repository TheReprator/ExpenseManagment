package dev.reprator.country

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
/**
 * The container to inject all dependencies from the language module.
 */
const val KOIN_NAMED_MAPPER = "countryMapper"


@Module
@ComponentScan("dev.reprator.country")
class CountryModule