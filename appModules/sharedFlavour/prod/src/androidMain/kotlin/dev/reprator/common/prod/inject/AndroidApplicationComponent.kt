package dev.reprator.common.prod.inject

import android.app.Application
import coil3.intercept.Interceptor
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
) : SharedApplicationComponent, ProdApplicationComponent {

    abstract val initializers: AppInitializers
    abstract val dispatchers: AppCoroutineDispatchers

    /**
     * We have no interceptors in the standard release currently
     */
    @Provides
    fun provideInterceptors(): Set<Interceptor> = emptySet()

    companion object
}
