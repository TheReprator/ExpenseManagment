package dev.reprator.common.qa.inject

import android.app.Application
import dev.reprator.common.appinitializers.AppInitializers
import dev.reprator.common.inject.SharedApplicationComponent
import dev.reprator.core.inject.ApplicationScope
import dev.reprator.core.util.AppCoroutineDispatchers
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
@ApplicationScope
abstract class AndroidApplicationComponent(
    @get:Provides val application: Application,
) : SharedApplicationComponent,
    QaApplicationComponent {

    abstract val initializers: AppInitializers
    abstract val dispatchers: AppCoroutineDispatchers

    companion object
}
