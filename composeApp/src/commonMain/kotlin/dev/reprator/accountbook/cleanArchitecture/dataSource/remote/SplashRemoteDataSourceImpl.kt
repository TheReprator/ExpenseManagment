package dev.reprator.accountbook.cleanArchitecture.dataSource.remote

import dev.reprator.accountbook.cleanArchitecture.data.dataSource.SplashRemoteDataSource
import dev.reprator.accountbook.cleanArchitecture.dataSource.remote.mapper.MapperSplash
import dev.reprator.accountbook.screen.splash.ModalSplashState
import me.tatarka.inject.annotations.Inject

@Inject
class SplashRemoteDataSourceImpl(private val apiService: ApiService, private val mapperSplash: MapperSplash) :
    SplashRemoteDataSource {

    override suspend fun splashRemoteDataSource(): ModalSplashState {
        val apiResult = apiService.splashData()

        if (apiResult.isSuccess) {
            val data = apiResult.getOrNull()!!
            return mapperSplash.map(data)
        }

        throw apiResult.exceptionOrNull()!!

    }

}