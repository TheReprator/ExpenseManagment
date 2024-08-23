package dev.reprator.accountbook.language.data.repositoryImpl.remote

import dev.reprator.accountbook.language.data.dataSource.LanguageRemoteDataSource
import dev.reprator.accountbook.language.data.repositoryImpl.remote.mapper.MapperLanguage
import dev.reprator.accountbook.language.modals.ModalStateLanguage
import dev.reprator.appFeatures.api.client.AppResult
import dev.reprator.appFeatures.api.client.AppSuccessModal
import dev.reprator.appFeatures.api.client.safeRequest
import dev.reprator.core.util.mapAll
import io.ktor.client.*
import io.ktor.http.*
import me.tatarka.inject.annotations.Inject

private const val ENDPOINT_LANGUAGE = "language"

@Inject
class LanguageRemoteDataSourceImpl(private val httpClient: HttpClient, private val mapperLanguage: MapperLanguage) :
    LanguageRemoteDataSource {

    override  suspend fun languageRemoteDataSource(): List<ModalStateLanguage>{

        val apiResult = httpClient.safeRequest<AppSuccessModal<List<EntityLanguage>>> {
            url {
                method = HttpMethod.Get
                path(ENDPOINT_LANGUAGE)
            }
        }
        
        return when(apiResult) {
            is AppResult.Success -> {
                mapperLanguage.mapAll(apiResult.body.data)
            }
            is AppResult.Error.HttpError -> {
                throw Exception(apiResult.errorMessage)
            }
            is  AppResult.Error.GenericError -> {
                throw Exception(apiResult.errorMessage)
            }
        }

    }

}