package dev.reprator.appFeatures.impl.performance

import dev.reprator.appFeatures.api.performance.Tracer
import com.google.firebase.perf.trace as firebaseTrace
import me.tatarka.inject.annotations.Inject

@Inject
class AndroidTracer : Tracer {
    private var enabled = true

    override fun trace(name: String, block: () -> Unit) {
        if (!enabled) {
            // If we've disabled ourselves, call block and return
            return block()
        }

        try {
            firebaseTrace(name) {
                block()
            }
        } catch (t: Throwable) {
            // Firebase likely isn't setup. Ignore the exception, but disable calls to Firebase
            // Performance Monitoring from now on
            enabled = false
            // Still need to call block too
            block()
        }
    }
}
