package dev.reprator.accountbook.language.domain.repository

import dev.reprator.accountbook.language.modals.ModalStateLanguage

interface LanguageRepository {
    suspend fun languageRepository(): List<ModalStateLanguage>
} 