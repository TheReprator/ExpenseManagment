package dev.reprator.language

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
/**
 * The container to inject all dependencies from the language module.
 */
const val KOIN_NAMED_MAPPER_LANGUAGE = "languageMapper"

@Module
@ComponentScan("dev.reprator.language")
class LanguageModule

/*
val languageModule = module {
    singleOf(::LanguageResponseMapper).withOptions {qualifier = named(KOIN_NAMED_MAPPER_LANGUAGE) } bind AppMapper::class
    single<LanguageRepository> { LanguageRepositoryImpl(get(qualifier = named(KOIN_NAMED_MAPPER_LANGUAGE))) }
    singleOf(::LanguageFacadeImpl) bind LanguageFacade::class
    single { LanguageControllerImpl(get()) } bind LanguageController::class
}
*/
