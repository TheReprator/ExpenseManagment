package dev.reprator.accountbook.domain.usecase

import dev.reprator.accountbook.domain.repository.SplashRepository
import dev.reprator.accountbook.modals.uiModal.ModalSplashState
import dev.reprator.core.util.Interactor
import dev.reprator.core.util.AppCoroutineDispatchers
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject

@Inject
class SplashUseCase(
    private val splashRepository: SplashRepository,
    private val dispatchers: AppCoroutineDispatchers,
) : Interactor<Unit, ModalSplashState>() {

    override suspend fun doWork(params: Unit): ModalSplashState {
        return withContext(dispatchers.io) {
            splashRepository.splashRepository()
        }
    }
}
