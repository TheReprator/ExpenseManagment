package dev.reprator.appFeatures.impl.performance

import dev.reprator.appFeatures.api.performance.Tracer
import dev.reprator.appFeatures.impl.firebaseFeatures.app.external.FirebaseApp
import dev.reprator.appFeatures.impl.firebaseFeatures.performance.FirebaseModalPerformance
import dev.reprator.appFeatures.impl.firebaseFeatures.performance.FirebaseModalTrace
import dev.reprator.appFeatures.impl.firebaseFeatures.performance.external.getPerformance
import dev.reprator.core.inject.ApplicationScope
import me.tatarka.inject.annotations.Provides

actual interface PerformanceComponent {

    @ApplicationScope
    @Provides
    fun provideAccountBookFirebaseTrace(firebaseApp: FirebaseApp): FirebaseModalTrace {
        val performance = getPerformance(firebaseApp)
        val modalPerformance =  FirebaseModalPerformance(performance).newTrace("Accountbook web")
        return modalPerformance
    }

    @ApplicationScope
    @Provides
    fun provideTracer(tracer: JsTracer): Tracer = tracer
}
