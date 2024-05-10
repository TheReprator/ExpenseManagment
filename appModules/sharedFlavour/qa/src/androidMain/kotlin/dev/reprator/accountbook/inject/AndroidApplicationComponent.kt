package dev.reprator.accountbook.inject

import android.app.Application
import dev.reprator.accountbook.appinitializers.AppInitializers
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
