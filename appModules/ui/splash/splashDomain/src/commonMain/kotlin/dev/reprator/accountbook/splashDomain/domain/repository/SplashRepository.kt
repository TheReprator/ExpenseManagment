package dev.reprator.accountbook.splashDomain.domain.repository

import dev.reprator.accountbook.splashDomain.ModalSplashState

interface SplashRepository {
    suspend fun splashRepository(): ModalSplashState
} 