package dev.reprator.accountbook.cleanArchitecture.data.repositoryImpl

import dev.reprator.accountbook.cleanArchitecture.data.dataSource.SplashRemoteDataSource
import dev.reprator.accountbook.cleanArchitecture.domain.repository.SplashRepository
import dev.reprator.accountbook.screen.splash.ModalSplashState
import me.tatarka.inject.annotations.Inject

@Inject
class SplashDataSourceImpl(
    private val splashRemoteDataSource: SplashRemoteDataSource
) : SplashRepository {

    companion object {
        private const val NO_INTERNET = "No internet connection detected."
    }

    override suspend fun splashRepository(): ModalSplashState {
        return splashRemoteDataSource.splashRemoteDataSource()
    }
}