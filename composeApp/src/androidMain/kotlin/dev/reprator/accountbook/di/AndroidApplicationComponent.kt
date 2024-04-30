package dev.reprator.accountbook.di

import android.app.Application
import dev.reprator.accountbook.utility.base.AppCoroutineDispatchers
import dev.reprator.accountbook.utility.impl.AppInitializers
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
@ApplicationScope
abstract class AndroidApplicationComponent(
  @get:Provides val application: Application,
) : SharedApplicationComponent {

  abstract val initializers: AppInitializers
  abstract val dispatchers: AppCoroutineDispatchers

  companion object
}
