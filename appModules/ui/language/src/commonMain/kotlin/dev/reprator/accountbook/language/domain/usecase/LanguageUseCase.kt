package dev.reprator.accountbook.language.domain.usecase

import dev.reprator.accountbook.language.domain.repository.LanguageRepository
import dev.reprator.accountbook.language.modals.ModalStateLanguage
import dev.reprator.core.util.Interactor
import dev.reprator.core.util.AppCoroutineDispatchers
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject

@Inject
class LanguageUseCase(
    private val languageRepository: LanguageRepository,
    private val dispatchers: AppCoroutineDispatchers,
) : Interactor<Unit, List<ModalStateLanguage>>() {

    override suspend fun doWork(params: Unit): List<ModalStateLanguage> {
        return withContext(dispatchers.io) {
            languageRepository.languageRepository()
        }
    }
}
