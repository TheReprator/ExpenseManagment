package dev.reprator.common.qa.inject

import dev.reprator.common.appinitializers.AppInitializers
import dev.reprator.common.inject.SharedApplicationComponent
import dev.reprator.core.inject.ApplicationScope
import me.tatarka.inject.annotations.Component

@Component
@ApplicationScope
abstract class DesktopApplicationComponent : SharedApplicationComponent, QaApplicationComponent {
    abstract val initializers: AppInitializers

    companion object
}
