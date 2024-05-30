package dev.reprator.appFeatures.impl.performance

import dev.reprator.appFeatures.api.performance.Tracer
import dev.reprator.appFeatures.impl.firebaseFeatures.performance.FirebaseModalTrace
import me.tatarka.inject.annotations.Inject

@Inject
class JsTracer(private val trace: FirebaseModalTrace) : Tracer {
    
    private var enabled = true

    override fun trace(name: String, block: () -> Unit) {
        if (!enabled) {
            // If we've disabled ourselves, call block and return
            return block()
        }

        try {
            trace.start()
            block()
            trace.stop()
        } catch (e: Exception) {
            console.log("vikram stack:: ${e.message}")
            // Firebase likely isn't setup. Ignore the exception, but disable calls to Firebase
            // Performance Monitoring from now on
            enabled = false
            // Still need to call block too
            block()
        }
    }
}
