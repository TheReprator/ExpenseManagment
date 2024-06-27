package dev.reprator.accountbook.language.data.repositoryImpl

import dev.reprator.accountbook.language.data.dataSource.LanguageRemoteDataSource
import dev.reprator.accountbook.language.domain.repository.LanguageRepository
import dev.reprator.accountbook.language.modals.ModalStateLanguage
import dev.reprator.appFeatures.api.utility.InternetChecker
import me.tatarka.inject.annotations.Inject

@Inject
class LanguageDataSourceImpl(
    private val languageRemoteDataSource: LanguageRemoteDataSource,
    private val internetChecker: InternetChecker
) : LanguageRepository {

    companion object {
        private const val NO_INTERNET = "No internet connection detected."
    }

    override suspend fun languageRepository(): List<ModalStateLanguage> {
         if(internetChecker.networkStatus.value)
            throw Exception(NO_INTERNET)
        return languageRemoteDataSource.languageRemoteDataSource()
    }
}