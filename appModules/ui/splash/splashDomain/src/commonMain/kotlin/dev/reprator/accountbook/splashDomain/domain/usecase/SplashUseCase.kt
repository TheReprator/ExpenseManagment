package dev.reprator.accountbook.splashDomain.domain.usecase

import dev.reprator.core.util.Interactor
import dev.reprator.accountbook.splashDomain.domain.repository.SplashRepository
import dev.reprator.accountbook.splashDomain.ModalSplashState
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
