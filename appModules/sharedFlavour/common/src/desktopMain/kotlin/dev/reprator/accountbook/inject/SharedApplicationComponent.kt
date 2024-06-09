package dev.reprator.accountbook.inject

import dev.reprator.accountbook.api.AccountBookApiComponent
import dev.reprator.appFeatures.impl.analytics.AnalyticsComponent
import dev.reprator.appFeatures.impl.logger.LoggerComponent
import dev.reprator.appFeatures.impl.performance.PerformanceComponent
import dev.reprator.appFeatures.impl.powerController.PowerControllerComponent
import dev.reprator.appFeatures.impl.preferences.PreferencesComponent
import dev.reprator.appFeatures.impl.utility.UtilityComponent
import dev.reprator.baseUi.imageLoading.ImageLoadingComponent
import dev.reprator.core.inject.ApplicationCoroutineScope
import dev.reprator.core.inject.ApplicationScope
import dev.reprator.core.util.AppCoroutineDispatchers
import kotlinx.coroutines.CoroutineScope
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
    PreferencesComponent, UtilityComponent, AccountBookApiComponent {

    @ApplicationScope
    @Provides
    fun provideApplicationCoroutineScope(
        dispatchers: AppCoroutineDispatchers,
    ): ApplicationCoroutineScope = CoroutineScope(dispatchers.main + SupervisorJob())
}
