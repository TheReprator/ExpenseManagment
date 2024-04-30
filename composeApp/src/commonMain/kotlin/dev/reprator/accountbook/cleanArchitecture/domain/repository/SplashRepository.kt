package dev.reprator.accountbook.cleanArchitecture.domain.repository

import dev.reprator.accountbook.screen.splash.ModalSplashState

interface SplashRepository {
    suspend fun splashRepository(): ModalSplashState
} 