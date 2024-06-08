package dev.reprator.accountbook.splashDomain.data.dataSource

import dev.reprator.accountbook.splashDomain.ModalSplashState

fun interface SplashRemoteDataSource {
    suspend fun splashRemoteDataSource(): ModalSplashState
}