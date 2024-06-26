package dev.reprator.accountbook.splash.domain.usecase

import dev.reprator.accountbook.splash.domain.repository.SplashRepository
import dev.reprator.accountbook.splash.modals.ModalStateSplash
import dev.reprator.core.util.Interactor
import dev.reprator.core.util.AppCoroutineDispatchers
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject

@Inject
class SplashUseCase(
    private val splashRepository: SplashRepository,
    private val dispatchers: AppCoroutineDispatchers,
) : Interactor<Unit, ModalStateSplash>() {

    override suspend fun doWork(params: Unit): ModalStateSplash {
        return withContext(dispatchers.io) {
            splashRepository.splashRepository()
        }
    }
}
