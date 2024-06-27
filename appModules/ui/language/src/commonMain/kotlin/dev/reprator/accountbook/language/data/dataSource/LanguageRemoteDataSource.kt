package dev.reprator.accountbook.language.data.dataSource

import dev.reprator.accountbook.language.modals.ModalStateLanguage


fun interface LanguageRemoteDataSource {
    suspend fun languageRemoteDataSource(): List<ModalStateLanguage>
}