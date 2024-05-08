package dev.reprator.common.prod.inject

import dev.reprator.common.appinitializers.AppInitializers
import dev.reprator.common.inject.SharedApplicationComponent
import dev.reprator.core.inject.ApplicationScope
import me.tatarka.inject.annotations.Component

@Component
@ApplicationScope
abstract class DesktopApplicationComponent : SharedApplicationComponent, ProdApplicationComponent {
    abstract val initializers: AppInitializers

    companion object
}
