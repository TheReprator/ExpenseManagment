package dev.reprator.accountbook.splash.data.dataSource

import dev.reprator.accountbook.splash.ModalSplashState

fun interface SplashRemoteDataSource {
    suspend fun splashRemoteDataSource(): ModalSplashState
}