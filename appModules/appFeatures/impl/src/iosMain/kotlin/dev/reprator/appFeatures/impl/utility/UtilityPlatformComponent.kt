package dev.reprator.appFeatures.impl.utility

import dev.reprator.appFeatures.api.utility.InternetChecker
import dev.reprator.core.inject.ApplicationScope
import kotlinx.cinterop.ExperimentalForeignApi
import me.tatarka.inject.annotations.Provides

actual interface UtilityPlatformComponent {
    @OptIn(ExperimentalForeignApi::class)
    @Provides
    @ApplicationScope
    fun provideInternetChecker(bind: IosInternetCheckerImpl): InternetChecker = bind
}