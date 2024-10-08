package dev.reprator.accountbook.splash.data.repositoryImpl

import dev.reprator.accountbook.splash.data.dataSource.SplashRemoteDataSource
import dev.reprator.accountbook.splash.domain.repository.SplashRepository
import dev.reprator.accountbook.splash.modals.ModalStateSplash
import dev.reprator.appFeatures.api.utility.InternetChecker
import me.tatarka.inject.annotations.Inject

@Inject
class SplashDataSourceImpl(
    private val splashRemoteDataSource: SplashRemoteDataSource,
    private val internetChecker: InternetChecker
) : SplashRepository {

    companion object {
        private const val NO_INTERNET = "No internet connection detected."
    }

    override suspend fun splashRepository(): ModalStateSplash {
        if(internetChecker.networkStatus.value)
            throw Exception(NO_INTERNET)
        return splashRemoteDataSource.splashRemoteDataSource()
    }
}