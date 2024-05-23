package dev.reprator.appFeatures.impl.utility

import dev.reprator.appFeatures.api.utility.InternetChecker
import dev.reprator.core.inject.ApplicationScope
import me.tatarka.inject.annotations.Provides

actual interface UtilityPlatformComponent {

    @Provides
    @ApplicationScope
    fun provideInternetChecker(bind: DesktopInternetCheckerImpl): InternetChecker = bind
}