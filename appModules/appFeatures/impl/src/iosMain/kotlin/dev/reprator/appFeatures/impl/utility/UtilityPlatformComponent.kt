package dev.reprator.appFeatures.impl.utility

import dev.reprator.appFeatures.api.utility.NetworkListener
import dev.reprator.core.inject.ApplicationScope
import me.tatarka.inject.annotations.Provides

actual interface UtilityPlatformComponent {
    @Provides
    @ApplicationScope
    fun provideInternetChecker(bind: IosNetworkListenerImpl): NetworkListener = bind
}