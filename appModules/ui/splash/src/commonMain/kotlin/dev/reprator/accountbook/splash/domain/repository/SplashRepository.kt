package dev.reprator.accountbook.splash.domain.repository

import dev.reprator.accountbook.splash.modals.ModalStateSplash

interface SplashRepository {
    suspend fun splashRepository(): ModalStateSplash
} 