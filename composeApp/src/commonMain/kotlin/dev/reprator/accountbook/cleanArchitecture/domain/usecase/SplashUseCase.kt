package dev.reprator.accountbook.cleanArchitecture.domain.usecase

import dev.reprator.accountbook.cleanArchitecture.domain.repository.SplashRepository
import dev.reprator.accountbook.screen.splash.ModalSplashState
import dev.reprator.accountbook.utility.base.AppCoroutineDispatchers
import dev.reprator.accountbook.utility.Interactor
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
