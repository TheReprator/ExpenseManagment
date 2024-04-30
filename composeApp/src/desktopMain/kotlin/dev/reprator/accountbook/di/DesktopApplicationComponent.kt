package dev.reprator.accountbook.di

import dev.reprator.accountbook.utility.impl.AppInitializers
import me.tatarka.inject.annotations.Component

@Component
@ApplicationScope
abstract class DesktopApplicationComponent : SharedApplicationComponent {
  abstract val initializers: AppInitializers
  companion object
}
