package dev.reprator.accountbook.inject

import dev.reprator.accountbook.appinitializers.AppInitializers
import dev.reprator.core.inject.ApplicationScope
import me.tatarka.inject.annotations.Component

@Component
@ApplicationScope
abstract class DesktopApplicationComponent : SharedApplicationComponent, QaApplicationComponent {
    abstract val initializers: AppInitializers

    companion object
}
