package dev.reprator.accountbook.splash.data.repositoryImpl

import dev.reprator.accountbook.splash.ModalSplashState
import dev.reprator.accountbook.splash.data.dataSource.SplashRemoteDataSource
import dev.reprator.accountbook.splash.domain.repository.SplashRepository
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