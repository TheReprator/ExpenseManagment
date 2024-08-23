package dev.reprator.accountbook.splash.data.dataSource

import dev.reprator.accountbook.splash.modals.ModalStateSplash

fun interface SplashRemoteDataSource {
    suspend fun splashRemoteDataSource(): ModalStateSplash
}