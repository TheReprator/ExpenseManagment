package dev.reprator.accountbook.domain.repository

import dev.reprator.accountbook.modals.uiModal.ModalSplashState

interface SplashRepository {
    suspend fun splashRepository(): ModalSplashState
} 