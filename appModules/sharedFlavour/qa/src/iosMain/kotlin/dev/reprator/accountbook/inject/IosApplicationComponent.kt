package dev.reprator.accountbook.inject

import dev.reprator.appFeatures.api.analytics.AppAnalytics
import dev.reprator.appFeatures.impl.logger.SetCrashReportingEnabledAction
import dev.reprator.accountbook.appinitializers.AppInitializers
import dev.reprator.appFeatures.api.utility.NetworkListener
import dev.reprator.core.inject.ApplicationScope
import me.tatarka.inject.annotations.Component

@Component
@ApplicationScope
abstract class IosApplicationComponent(
    override val analytics: AppAnalytics,
    override val setNetworkListener: NetworkListener,
    override val setCrashReportingEnabledAction: SetCrashReportingEnabledAction
) : SharedApplicationComponent, QaApplicationComponent {

    abstract val initializers: AppInitializers

    companion object
}
