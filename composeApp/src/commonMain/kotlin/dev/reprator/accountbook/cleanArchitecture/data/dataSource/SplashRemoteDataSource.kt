package dev.reprator.accountbook.cleanArchitecture.data.dataSource

import dev.reprator.accountbook.screen.splash.ModalSplashState

fun interface SplashRemoteDataSource {
    suspend fun splashRemoteDataSource(): ModalSplashState
}