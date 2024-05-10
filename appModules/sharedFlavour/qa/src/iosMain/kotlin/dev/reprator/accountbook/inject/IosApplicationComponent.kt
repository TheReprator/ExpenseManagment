package dev.reprator.accountbook.inject

import dev.reprator.appFeatures.api.analytics.Analytics
import dev.reprator.appFeatures.impl.logger.SetCrashReportingEnabledAction
import dev.reprator.accountbook.appinitializers.AppInitializers
import dev.reprator.core.inject.ApplicationScope
import me.tatarka.inject.annotations.Component

@Component
@ApplicationScope
abstract class IosApplicationComponent(
    override val analytics: Analytics,
    override val setCrashReportingEnabledAction: SetCrashReportingEnabledAction,
) : SharedApplicationComponent, QaApplicationComponent {

    abstract val initializers: AppInitializers

    companion object
}
