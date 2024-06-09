package dev.reprator.accountbook.data.dataSource

import dev.reprator.accountbook.modals.uiModal.ModalSplashState

fun interface SplashRemoteDataSource {
    suspend fun splashRemoteDataSource(): ModalSplashState
}