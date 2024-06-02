package dev.reprator.accountbook.splash.domain.repository

import dev.reprator.accountbook.splash.ModalSplashState

interface SplashRepository {
    suspend fun splashRepository(): ModalSplashState
} 