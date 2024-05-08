package dev.reprator.common.inject

import dev.reprator.appFeatures.impl.analytics.AnalyticsComponent
import dev.reprator.appFeatures.impl.logger.LoggerComponent
import dev.reprator.appFeatures.impl.performance.PerformanceComponent
import dev.reprator.appFeatures.impl.powerController.PowerControllerComponent
import dev.reprator.appFeatures.impl.preferences.PreferencesComponent
import dev.reprator.baseUi.imageLoader.ImageLoadingComponent
import dev.reprator.core.inject.ApplicationCoroutineScope
import dev.reprator.core.inject.ApplicationScope
import dev.reprator.core.util.AppCoroutineDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import me.tatarka.inject.annotations.Provides

expect interface SharedPlatformApplicationComponent

interface SharedApplicationComponent :
    SharedPlatformApplicationComponent,
    ImageLoadingComponent,
    AnalyticsComponent,
    LoggerComponent,
    PerformanceComponent,
    PowerControllerComponent,
    PreferencesComponent {

    @OptIn(ExperimentalCoroutinesApi::class)
    @ApplicationScope
    @Provides
    fun provideCoroutineDispatchers(): AppCoroutineDispatchers = AppCoroutineDispatchers(
        io = Dispatchers.IO,
        databaseWrite = Dispatchers.IO.limitedParallelism(1),
        databaseRead = Dispatchers.IO.limitedParallelism(4),
        computation = Dispatchers.Default,
        main = Dispatchers.Main,
    )

    @ApplicationScope
    @Provides
    fun provideApplicationCoroutineScope(
        dispatchers: AppCoroutineDispatchers,
    ): ApplicationCoroutineScope = CoroutineScope(dispatchers.main + SupervisorJob())
}
