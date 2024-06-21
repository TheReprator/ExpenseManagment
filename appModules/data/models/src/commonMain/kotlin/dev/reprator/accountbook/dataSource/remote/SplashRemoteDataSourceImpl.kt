package dev.reprator.accountbook.dataSource.remote

import dev.reprator.accountbook.api.ApiService
import dev.reprator.accountbook.data.dataSource.SplashRemoteDataSource
import dev.reprator.accountbook.dataSource.remote.mapper.MapperSplash
import dev.reprator.accountbook.modals.networkModal.EntitySplash
import dev.reprator.accountbook.modals.uiModal.ModalSplashState
import me.tatarka.inject.annotations.Inject

@Inject
class SplashRemoteDataSourceImpl(private val apiService: ApiService, private val mapperSplash: MapperSplash) :
    SplashRemoteDataSource {

    override suspend fun splashRemoteDataSource(): ModalSplashState {
        throw Exception("error")
//        val apiResult = apiService.splashData<EntitySplash>()
//
//        if (apiResult.isSuccess) {
//            val data = apiResult.getOrNull()!!
//            return mapperSplash.map(data)
//        }
//
//        throw apiResult.exceptionOrNull()!!

    }

}