package dev.reprator.accountbook.splash.data.repositoryImpl.remote

import dev.reprator.accountbook.splash.data.dataSource.SplashRemoteDataSource
import dev.reprator.accountbook.splash.data.repositoryImpl.remote.mapper.MapperSplash
import dev.reprator.accountbook.splash.modals.ModalStateSplash
import dev.reprator.appFeatures.api.client.AppResult
import dev.reprator.appFeatures.api.client.AppSuccessModal
import dev.reprator.appFeatures.api.client.safeRequest
import io.ktor.client.*
import io.ktor.http.*
import me.tatarka.inject.annotations.Inject

private const val ENDPOINT_SPLASH = "splash"

@Inject
class SplashRemoteDataSourceImpl(private val httpClient: HttpClient, private val mapperSplash: MapperSplash) :
    SplashRemoteDataSource {

    override suspend fun splashRemoteDataSource(): ModalStateSplash {

        val apiResult = httpClient.safeRequest<AppSuccessModal<EntitySplash>> {
            url {
                method = HttpMethod.Get
                path(ENDPOINT_SPLASH)
            }
        }
        
        return when(apiResult) {
            is AppResult.Success -> mapperSplash.map(apiResult.body.data)
            is AppResult.Error.HttpError -> {
                throw Exception(apiResult.errorMessage)
            }
            is  AppResult.Error.GenericError -> {
                throw Exception(apiResult.errorMessage)
            }
        }

    }

}