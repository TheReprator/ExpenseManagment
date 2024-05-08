package dev.reprator.accountbook.splash.dataSource.remote

import dev.reprator.accountbook.splash.ModalSplashState
import dev.reprator.accountbook.splash.data.dataSource.SplashRemoteDataSource
import dev.reprator.accountbook.splash.dataSource.remote.mapper.MapperSplash
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