package dev.reprator.appFeatures.impl.performance

import dev.reprator.appFeatures.api.performance.Tracer
import dev.reprator.core.inject.ApplicationScope
import me.tatarka.inject.annotations.Provides

actual interface PerformanceComponent {

    @ApplicationScope
    @Provides
    fun provideTracer(tracer: JsTracer): Tracer = tracer
}
