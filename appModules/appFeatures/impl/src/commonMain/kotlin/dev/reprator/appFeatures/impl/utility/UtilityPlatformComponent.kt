package dev.reprator.appFeatures.impl.utility

import dev.reprator.appFeatures.api.utility.ApplicationLifeCycle
import dev.reprator.core.inject.ApplicationScope
import me.tatarka.inject.annotations.Provides


expect interface UtilityPlatformComponent

interface UtilityComponent : UtilityPlatformComponent {
    val applicationLifeCycle: ApplicationLifeCycle

    @Provides
    @ApplicationScope
    fun provideApplicationLifeCycle(bind: ApplicationLifeCycleImpl): ApplicationLifeCycle = bind
}
