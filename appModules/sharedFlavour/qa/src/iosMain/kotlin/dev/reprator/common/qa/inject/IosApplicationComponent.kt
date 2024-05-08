package dev.reprator.common.qa.inject

import dev.reprator.appFeatures.api.analytics.Analytics
import dev.reprator.appFeatures.impl.logger.SetCrashReportingEnabledAction
import dev.reprator.common.appinitializers.AppInitializers
import dev.reprator.common.inject.SharedApplicationComponent
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
